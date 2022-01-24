package com.testproject.urltrimmer.util;

import com.testproject.urltrimmer.to.UserTo;
import com.testproject.urltrimmer.util.exception.IllegalRequestDataException;

public final class ValidationUtil {

    public static void assureIdConsistent(UserTo to, int id) {
        if (isNew(to.getId()) && to.getId()!=id) throw new IllegalRequestDataException(to + " must be with id=" + id);
    }

    public static void checkNew(UserTo to) {
        if (isNew(to.getId())) throw new IllegalRequestDataException(to + " must be new (id=null)");
    }

    private static boolean isNew (Integer id){
        return id != null;
    }
}
