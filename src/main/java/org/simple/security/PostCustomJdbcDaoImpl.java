package org.simple.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.simple.dto.SaltedUserDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class PostCustomJdbcDaoImpl extends JdbcDaoImpl {
	public void changePassword(String username, String password) {
		getJdbcTemplate().update("update sim_user set password = ? where name = ?", password,username);
	}

	@Override
	protected UserDetails createUserDetails(String username,
			UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();
		if (!isUsernameBasedPrimaryKey()) {
			returnUsername = username;
		}
		SaltedUserDTO saltedUserDTO = new SaltedUserDTO();
		saltedUserDTO.setUsername(returnUsername);
		saltedUserDTO.setPassword(userFromUserQuery.getPassword());
		saltedUserDTO.setEnabled(userFromUserQuery.isEnabled());
		saltedUserDTO.setAccountNonExpired(true);
		saltedUserDTO.setCredentialsNonExpired(true);
		saltedUserDTO.setAccountNonLocked(true);
		saltedUserDTO.setAuthorities(combinedAuthorities);
		saltedUserDTO.setSalt(((SaltedUser) userFromUserQuery).getSalt());
		return new SaltedUser(saltedUserDTO);
	}

	@Override
	protected List<UserDetails> loadUsersByUsername(String username) {
		RowMapper<UserDetails> rowMapper = new RowMapper<UserDetails>() {
			@Override
			public UserDetails mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String username = rs.getString(1);
				String password = rs.getString(2);
				boolean enabled = rs.getBoolean(3);
				String salt = rs.getString(4);

				SaltedUserDTO saltedUserDTO = new SaltedUserDTO();
				saltedUserDTO.setUsername(username);
				saltedUserDTO.setPassword(password);
				saltedUserDTO.setEnabled(enabled);
				saltedUserDTO.setAccountNonExpired(true);
				saltedUserDTO.setCredentialsNonExpired(true);
				saltedUserDTO.setAccountNonLocked(true);
				saltedUserDTO.setAuthorities(AuthorityUtils.NO_AUTHORITIES);
				saltedUserDTO.setSalt(salt);
				return new SaltedUser(saltedUserDTO);
			}
		};
		return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] { username }, rowMapper);
	}
}