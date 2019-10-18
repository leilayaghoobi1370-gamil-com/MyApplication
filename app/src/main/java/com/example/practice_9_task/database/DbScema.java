package com.example.practice_9_task.database;

public class DbScema {
    public static final String DB_NAME = "task.db";

    public static final class TABLE {
        public static final String TABLE_NAME = "task";

        public static final class Cols {
            public final static String ID = " _id ";
            public final static String Title = "_title";
            public final static String kEYMODLE = " _keymodle ";
            public final static String DESCRIPTION = "_description";
            public final static String DATE = "_date";
            public final static String TIME = "_time";
            public final static String STATE ="_state";
            public final static String STATEBOOL ="_statebool";
            public final static String SPINNERSTATE = "_spinnerState";
            public final static String IDUSER = "_iduser";

        }

    }
    public static final class USER {
        public static final String TABLE_NAME = "username";

        public static final class Cols {
            public final static String ID = "_id";
            public final static String NAME = " _name";
            public final static String PASSWIRDID = " _password";


        }

    }

}

