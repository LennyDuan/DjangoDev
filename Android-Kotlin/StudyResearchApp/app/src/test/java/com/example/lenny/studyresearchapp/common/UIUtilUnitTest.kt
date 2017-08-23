package com.example.lenny.studyresearchapp.common

/**
 * Created by Lenny on 23/08/2017.
 */
import org.junit.Assert
import org.junit.Test

/**
 * Created by Lenny on 21/08/2017.
 */

class UIUtilUnitTest {
    @Test
    fun returnTrueForValidDate() {
        Assert.assertEquals(true, UIUtil.validDateInput("2017-12-20"))
    }

    @Test
    fun returnTrueForSingleMonthValidDate() {
        Assert.assertEquals(true, UIUtil.validDateInput("2017-4-20"))
    }

    @Test
    fun returnFalseForInvalidDate() {
        Assert.assertEquals(false, UIUtil.validDateInput("2017-02-213"))
    }


    @Test  //foo@demo.net
    fun returnTrueForValidEmail() {
        Assert.assertEquals(true, UIUtil.validEmailInput("foo@demo.net"))
    }

    @Test  //foo@aber.ac.uk
    fun returnTrueForValidEmail2() {
        Assert.assertEquals(true, UIUtil.validEmailInput("foo@aber.ac.uk"))
    }


    @Test //bar.ba@test
    fun returnTrueForInValidEmail() {
        Assert.assertEquals(false, UIUtil.validEmailInput("bar.ba@test"))
    }
}