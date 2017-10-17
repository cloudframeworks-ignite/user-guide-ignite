package org.springframework.samples.petclinic.visits.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.springframework.samples.petclinic.visits.model.Visit;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.CacheStoreSessionResource;

public class CacheJdbcVisitStore extends CacheStoreAdapter<Long, Visit> {

	@CacheStoreSessionResource
	private CacheStoreSession ses;

	@Override
	public Visit load(Long key) {
		System.out.println(">>> Store load [key=" + key + ']');
		try (Connection conn = connection()) {
			try (PreparedStatement st = conn
					.prepareStatement("select * from visits where id = ?")) {
				st.setString(1, key.toString());

				ResultSet rs = st.executeQuery();

				return rs.next() ? new Visit(rs.getInt(1), rs.getInt(2),
						rs.getString(3), rs.getString(4)) : null;
			} catch (SQLException e) {
				throw new CacheLoaderException("Failed to load object [key="
						+ key + ']', e);
			}
		}catch (SQLException e) {
		      throw new CacheLoaderException("Failed to load: " + key, e);
	    }
	}

	@Override
	public void write(Cache.Entry<? extends Long, ? extends Visit> entry) {
		Long key = entry.getKey();
		Visit val = entry.getValue();

		System.out
				.println(">>> Store write [key=" + key + ", val=" + val + ']');

		try {
			Connection conn = connection();
			int updated;
			// Try update first. If it does not work, then try insert.
			// Some databases would allow these to be done in one 'upsert'
			// operation.
			try (PreparedStatement st = conn
					.prepareStatement("update visits set pet_id = ? where id = ?")) {
				st.setInt(1, val.petId);
				st.setLong(2, val.id);

				updated = st.executeUpdate();
			}
			// If update failed, try to insert.
			if (updated == 0) {
				try (PreparedStatement st = conn
						.prepareStatement("insert into visits (id, pet_id, visit_date, description) values (?, ?, ?, ?)")) {
					st.setLong(1, val.id);
					st.setInt(2, val.petId);
					st.setString(3, val.date);
					st.setString(4, val.description);
					st.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new CacheWriterException("Failed to write object [key=" + key
					+ ", val=" + val + ']', e);
		}
	}

	@Override
	public void delete(Object key) {
		System.out.println(">>> Store delete [key=" + key + ']');
		try (Connection conn = connection()) {
			try (PreparedStatement st = conn
					.prepareStatement("delete from visits where id=?")) {
				st.setLong(1, (Long) key);

				st.executeUpdate();
			} catch (SQLException e) {
				throw new CacheWriterException("Failed to delete object [key="
						+ key + ']', e);
			}
		}catch (SQLException e) {
		      throw new CacheLoaderException("Failed to load: " + key, e);
	    }
	}

	@Override
	public void loadCache(IgniteBiInClosure<Long, Visit> clo, Object... objects) {

		try (Connection conn = connection()) {
			try (PreparedStatement stmt = conn
					.prepareStatement("select * from visits")) {
				ResultSet rs = stmt.executeQuery();
				int cnt = 0;
				while (rs.next()) {
					Visit visit = new Visit(rs.getInt(1), rs.getInt(2),
							rs.getString(3), rs.getString(4));
					clo.apply(visit.id, visit);
					cnt++;
				}
				System.out.println(">>> Loaded " + cnt + " values into cache.");
			} catch (SQLException e) {
				throw new CacheLoaderException(
						"Failed to load values from cache store.", e);
			}
		}catch (SQLException e) {
		      throw new CacheLoaderException("Failed to load: ", e);
	    }
	}

	private Connection connection() throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://mysql:3306/petclinic", "root", "root");

		conn.setAutoCommit(true);

		return conn;
	}
}