package com.example.dutchman.messcostcalc.constants;

/**
 * Created by dutchman on 7/11/17.
 */

public class DatabaseConstant {

    public abstract class COLUMN{

        public static final String KEY_ID = "id";
        public static final String KEY_DATE = "date";
        public static final String KEY_MONTH = "month";
        public static final String KEY_YEAR = "year";
        public static final String KEY_TK = "tk";

    }

    public class MemberTB{

        public static final String NAME = "MEMBER_INFO";

        public class COL extends COLUMN{

            public static final String KEY_MEMBER_NAME = "person_name";
            public static final String KEY_ADVANCE_TK = "advance_tk";
            public static final String KEY_IS_AVAILABLE = "isAvailable";
        }

    }

    public class BazarTB{

        public static final String NAME = "bazar_info";

        public class COL extends COLUMN{

            public static final String KEY_MEMBER_NAME = "person_name";


        }
    }

    public class MealTB{
        public static final String NAME = "meal_info";

        public class COL extends COLUMN{
            public static final String KEY_MEMBER_NAME = "person_name";
            public static final String KEY_MEAL = "meal";
        }

    }

    public class MealDebitCreditTB{

        public static final String NAME = "bazar_per_credit";

        public class COL extends COLUMN{

            public static final String KEY_MEMBER_NAME = "person_name";
        }
    }

    public class RentInfo{

        public static final String NAME = "rent_info";

        public class COL extends COLUMN{

            public static final String KEY_H_RENT       = "house_rent";
            public static final String KEY_GUS_CURRENT  = "gus_current";
            public static final String KEY_SERVENT      = "servent";
            public static final String KEY_NET_BILL     = "net_bill";
            public static final String KEY_PAPER        = "paper";
            public static final String KEY_DIRST        = "dirst";
            public static final String KEY_OTHERS       = "others";
            public static final String KEY_MEMBERS      = "members";
            public static final String KEY_TOTAL_RENT   = "total_rent";
            public static final String KEY_PERHEAD      = "perhead";
        }
    }

    public class RentDebitCreditTB{

        public static final String NAME = "rent_per_credit";

        public class COL extends COLUMN{

            public static final String KEY_MEMBER_NAME = "person_name";
        }
    }


}
