package org.springframework.samples.vetclinic.vets.store;

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
import org.springframework.samples.petclinic.vets.model.Vet;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.CacheStoreSessionResource;

public class CacheJdbcVetStore extends CacheStoreAdapter<Long, Vet> {

	@CacheStoreSessionResource
	private CacheStoreSession ses;

	@Override
	public Vet load(Long key) {
		System.out.println(">>> Store load [key=" + key + ']');
		try (Connection conn = connection()) {
			try (PreparedStatement st = conn
					.prepareStatement("select * from vets where id = ?")) {
				st.setString(1, key.toString());

				ResultSet rs = st.executeQuery();

				return rs.next() ? new Vet(rs.getInt(1), rs.getString(2),
						rs.getString(3)) : null;
			} catch (SQLException e) {
				throw new CacheLoaderException("Failed to load object [key="
						+ key + ']', e);
			}
		} catch (SQLException e) {
			throw new CacheLoaderException("Failed to load: " + key, e);
		}
	}

	@Override
	public void write(Cache.Entry<? extends Long, ? extends Vet> entry) {
		Long key = entry.getKey();
		Vet val = entry.getValue();

		System.out
				.println(">>> Store write [key=" + key + ", val=" + val + ']');

		try {
			Connection conn = connection();

			try (PreparedStatement st = conn
					.prepareStatement("insert into vets (id, first_name, last_name) values (?, ?, ?)")) {
				st.setLong(1, val.id);
				st.setString(2, val.firstName);
				st.setString(3, val.lastName);
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
					.prepareStatement("delete from vets where id=?")) {
				st.setLong(1, (Long) key);

				st.executeUpdate();
			} catch (SQLException e) {
				throw new CacheWriterException("Failed to delete object [key="
						+ key + ']', e);
			}
		} catch (SQLException e) {
			throw new CacheLoaderException("Failed to load: " + key, e);
		}
	}

	@Override
	public void loadCache(IgniteBiInClosure<Long, Vet> clo, Object... objects) {

		try (Connection conn = connection()) {
			try (PreparedStatement stmt = conn
					.prepareStatement("select * from vets")) {
				ResultSet rs = stmt.executeQuery();
				int cnt = 0;
				while (rs.next()) {
					Vet vet = new Vet(rs.getInt(1), rs.getString(2),
							rs.getString(3));
					clo.apply(vet.id, vet);
					cnt++;
				}
				System.out.println(">>> Loaded " + cnt + " values into cache.");
			} catch (SQLException e) {
				throw new CacheLoaderException(
						"Failed to load values from cache store.", e);
			}
		} catch (SQLException e) {
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