package org.talk.auth.consts;

import org.talk.auth.model.User;

public class UserConst {
    public static int EMAIL_VERIFIED = 0;
    public static long EMAIL_VERIFIED_BIT = 1L << EMAIL_VERIFIED;

    public static boolean getEmailVerified(User user) {
        return ((user.getFields() >>> EMAIL_VERIFIED) & 1) == 1;
    }

    public static void setEmailVerified(User user) {
        user.setFields((user.getFields() | (1L << EMAIL_VERIFIED)));
    }
}
