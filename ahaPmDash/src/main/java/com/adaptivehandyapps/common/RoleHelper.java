package com.adaptivehandyapps.common;

import com.adaptivehandyapps.ahaclouddal.AhaOpsStage.OpsStage;

public class RoleHelper {
	public static boolean hasBasicRights(String role, OpsStage stage)
	{
		if( role.equals("*") || role.equals("or") || role.equals("director") || role.equals("oa") )
		{
			return true;
		}
		
		switch( stage )
		{
		case CLEAN:
			return role.equals("hk") || role.equals("hksuper");
		case HOTTUB:
			return role.equals("ht") || role.equals("htsuper");
		case LINEN:
			return role.equals("ld") || role.equals("ldsuper");
		case MAINT:
			return role.equals("mt") || role.equals("mtsuper");
		default:
			break;
		}
		
		return false;
	}
	
	public static boolean hasAdvancedRights(String role)
	{
		if( role.equals("*") || role.equals("or") || role.equals("director") || role.equals("oa") || role.contains("super") )
			return true;
		
		return false;
	}
	
}
