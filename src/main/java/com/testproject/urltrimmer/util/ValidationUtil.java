package com.testproject.urltrimmer.util;

import com.testproject.urltrimmer.model.UserTo;
import com.testproject.urltrimmer.util.exception.IllegalRequestDataException;

public final class ValidationUtil {

//    public static void assureIdConsistent(HasId bean, int id) {
//        if (bean.isNew()) {
//            bean.setId(id);
//        } else if (bean.id() != id) {
//            throw new IllegalRequestDataException(bean + " must be with id=" + id);
//        }
//    }

    public static void checkNew(UserTo to) {
        if (!isNew(to.getId())) {
            throw new IllegalRequestDataException(to + " must be new (id=null)");
        }
    }

    private static boolean isNew (Integer id){
        return id == null;
    }
}
