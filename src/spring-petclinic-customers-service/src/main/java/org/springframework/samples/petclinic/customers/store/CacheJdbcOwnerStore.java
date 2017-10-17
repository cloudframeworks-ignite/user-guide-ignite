package org.springframework.samples.petclinic.customers.store;

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
import org.springframework.samples.petclinic.customers.model.Owner;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.CacheStoreSessionResource;

public class CacheJdbcOwnerStore extends CacheStoreAdapter<Long, Owner> {

	@CacheStoreSessionResource
	private CacheStoreSession ses;

	@Override
	public Owner load(Long key) {
		System.out.println(">>> Store load [key=" + key + ']');
		try (Connection conn = connection()) {
			try (PreparedStatement st = conn
					.prepareStatement("select * from owners where id = ?")) {
				st.setString(1, key.toString());

				ResultSet rs = st.executeQuery();

				return rs.next() ? new Owner(rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)) : null;
			} catch (SQLException e) {
				throw new CacheLoaderException("Failed to load object [key="
						+ key + ']', e);
			}
		}catch (SQLException e) {
		      throw new CacheLoaderException("Failed to load: " + key, e);
	    }
	}

	@Override
	public void write(Cache.Entry<? extends Long, ? extends Owner> entry) {
		Long key = entry.getKey();
		Owner val = entry.getValue();

		System.out
				.println(">>> Store write [key=" + key + ", val=" + val + ']');

		try {
			Connection conn = connection();
			int updated;
			try (PreparedStatement st = conn
					.prepareStatement("update owners set first_name = ?,last_name=?,address=?,city=?,telephone=? where id = ?")) {
				st.setString(1, val.firstName);
				st.setString(2, val.lastName);
				st.setString(3, val.address);
				st.setString(4, val.city);
				st.setString(5, val.telephone);
				st.setLong(6, val.id);

				updated = st.executeUpdate();
			}
			if (updated == 0) {
				try (PreparedStatement st = conn
						.prepareStatement("insert into owners (id, first_name, last_name, address, city, telephone) values (?, ?, ?, ?,?,?)")) {
					st.setLong(1, val.id);
					st.setString(2, val.firstName);
					st.setString(3, val.lastName);
					st.setString(4, val.address);
					st.setString(5, val.city);
					st.setString(6, val.telephone);
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
					.prepareStatement("delete from owners where id=?")) {
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
	public void loadCache(IgniteBiInClosure<Long, Owner> clo, Object... objects) {

		try (Connection conn = connection()) {
			try (PreparedStatement stmt = conn
					.prepareStatement("select * from owners")) {
				ResultSet rs = stmt.executeQuery();
				int cnt = 0;
				while (rs.next()) {
					Owner owner = new Owner(rs.getInt(1), rs.getString(2),
							rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
					clo.apply(owner.id, owner);
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