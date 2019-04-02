
package utiles;

import security.LoginService;

public class AuthorityMethods {

	/**
	 * Recibe la autoridad que quieres comprobar que el usuario loggeado tiene. <br/>
	 * <b>True</b>: <u>si es la misma</u> <br/>
	 * <b>False</b>: <u>si no lo es</u>
	 * 
	 * @param String
	 *            authority
	 * @return boolean
	 */
	public static boolean chechAuthorityLogged(final String authority) {
		boolean res = false;

		final String auth = LoginService.getPrincipal().getAuthorities().iterator().next().getAuthority();

		res = (auth.equals(authority)) ? true : false;

		return res;
	}

}
