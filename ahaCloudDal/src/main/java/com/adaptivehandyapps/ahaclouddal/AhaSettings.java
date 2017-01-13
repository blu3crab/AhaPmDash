// Project: AHA Device IO Service
// Contributor(s): M.A.Tucker
// Origination: JUL 2014
// Copyright © 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.
package com.adaptivehandyapps.ahaclouddal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AhaSettings {
//	////////////////////////////////////////////////////////
//	// AHA roles
//	public static final String AHA_ROLE_OWNER = "owner";
//	public static final String AHA_ROLE_RENTER = "renter";
//	public static final String AHA_ROLE_DIRECTOR = "director";
//	public static final String AHA_ROLE_ADMIN = "admin";
//	public static final String AHA_ROLE_OWNERREP = "or";
//	public static final String AHA_ROLE_HKSUPER = "hksuper";
//	public static final String AHA_ROLE_HK = "hk";
//	public static final String AHA_ROLE_LDSUPER = "ldsuper";
//	public static final String AHA_ROLE_LD = "ld";
//	public static final String AHA_ROLE_HTSUPER = "htsuper";
//	public static final String AHA_ROLE_HT = "ht";
//	public static final String AHA_ROLE_MTSUPER = "mtsuper";
//	public static final String AHA_ROLE_MT = "mt";
//	public static final List<String> AHA_ROLE = new ArrayList<String>(
//			Arrays.asList(
//					AHA_ROLE_OWNER, AHA_ROLE_RENTER,
//					AHA_ROLE_DIRECTOR, AHA_ROLE_ADMIN, AHA_ROLE_OWNERREP,
//					AHA_ROLE_HKSUPER, AHA_ROLE_HK, AHA_ROLE_LDSUPER, AHA_ROLE_LD,
//					AHA_ROLE_HTSUPER, AHA_ROLE_HT, AHA_ROLE_MTSUPER, AHA_ROLE_MT
//					));
	////////////////////////////////////////////////////////
	// AHA object active indicator
	public static final Integer INTEGER_UNDEFINED = -1;

	////////////////////////////////////////////////////////
	// AHA object active indicator
	public static Integer OBJ_ACTIVE = 1;	// object is active
	public static Integer OBJ_INACTIVE = 0;	// object is inactive
	public static Integer OBJ_FLUSH = -1;	// object is ready to be flushed

}
