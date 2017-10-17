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
import org.springframework.samples.petclinic.customers.model.PetType;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.CacheStoreSessionResource;

public class CacheJdbcPetTypeStore extends CacheStoreAdapter<Long, PetType> {

	@CacheStoreSessionResource
	private CacheStoreSession ses;

	@Override
	public PetType load(Long key) {
		System.out.println(">>> Store load [key=" + key + ']');
		try (Connection conn = connection()) {
			try (PreparedStatement st = conn
					.prepareStatement("select * from types where id = ?")) {
				st.setString(1, key.toString());

				ResultSet rs = st.executeQuery();

				return rs.next() ? new PetType(rs.getInt(1), rs.getString(2)) : null;
			} catch (SQLException e) {
				throw new CacheLoaderException("Failed to load object [key="
						+ key + ']', e);
			}
		}catch (SQLException e) {
		      throw new CacheLoaderException("Failed to load: " + key, e);
	    }
	}

	@Override
	public void write(Cache.Entry<? extends Long, ? extends PetType> entry) {
		Long key = entry.getKey();
		PetType val = entry.getValue();

		System.out
				.println(">>> Store write [key=" + key + ", val=" + val + ']');

		try {
			Connection conn = connection();
			
			try (PreparedStatement st = conn
					.prepareStatement("insert into types (id, name) values (?, ?)")) {
				st.setLong(1, val.id);
				st.setString(2, val.name);
				st.executeUpdate();
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
					.prepareStatement("delete from types where id=?")) {
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
	public void loadCache(IgniteBiInClosure<Long, PetType> clo, Object... objects) {

		try (Connection conn = connection()) {
			try (PreparedStatement stmt = conn
					.prepareStatement("select * from types")) {
				ResultSet rs = stmt.executeQuery();
				int cnt = 0;
				while (rs.next()) {
					PetType petType = new PetType(rs.getInt(1), rs.getString(2));
					clo.apply(petType.id, petType);
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