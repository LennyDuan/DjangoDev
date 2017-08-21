package com.example.lenny.studyresearchapp.common

import org.junit.Assert
import org.junit.Test

/**
 * Created by Lenny on 21/08/2017.
 */

class TypeUtilUnitTest {
    @Test
    fun compareDateReturnTrueIfEndDateExpired() {
        Assert.assertEquals(true, TypeUtil.compareDateExpired("2017-8-20"))
    }

    @Test
    fun compareDateReturnFalseIfEndDateNotExpired() {
        Assert.assertEquals(false, TypeUtil.compareDateExpired("2027-8-10"))
    }

    @Test
    fun compareDateReturnFalseIfEndDateNotExpiredRecently() {
        Assert.assertEquals(false, TypeUtil.compareDateExpired("2017-9-30"))
    }
}
