
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

	/**
	 * Intenta coger la cuenta loggeada, si puede<br/>
	 * se debe a que hay alguien loggeado. Si no, fallar�. <br/>
	 * <b>True</b>:<u>si hay alguien loggeado</u><br/>
	 * <b>False</b>:<u>no hay nadie loggeado</u>
	 * 
	 * @return boolean
	 */
	public static boolean checkIsSomeoneLogged() {
		boolean res = true;

		try {
			LoginService.getPrincipal();
		} catch (final Throwable oops) {
			res = false;
		}

		return res;
	}

}
