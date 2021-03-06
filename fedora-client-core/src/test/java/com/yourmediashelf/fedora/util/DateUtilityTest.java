/**
 * Copyright (C) 2010 MediaShelf <http://www.yourmediashelf.com/>
 *
 * This file is part of fedora-client.
 *
 * fedora-client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fedora-client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fedora-client.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.yourmediashelf.fedora.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 *
 *
 * @author Edwin Shin
 */
public class DateUtilityTest {

    @Test
    public void testFormatting() throws Exception {
        String s = "1986-11-05T05:57:17.3Z";
        DateTime dt = DateUtility.parseXSDDateTime(s);
        assertEquals(s, DateUtility.getXSDDateTime(dt));
    }

    @Test
    public void testTimezones() throws Exception {
        String s1 = "2002-10-10T12:00:00-05:00";
        DateTime dt1 = DateUtility.parseXSDDateTime(s1);
        String s2 = "2002-10-10T17:00:00-00:00";
        DateTime dt2 = DateUtility.parseXSDDateTime(s2);
        assertTrue(dt1.isEqual(dt2));

        String s3 = "2002-10-10T12:00:00Z";
        DateTime dt3 = DateUtility.parseXSDDateTime(s3);
        assertTrue(dt2.minusHours(5).isEqual(dt3));
    }

    @Test
    public void testZeroYearAndEndFrag() throws Exception {
        String s4 = "-0001-12-31T24:00:00Z";
        DateTime dt4 = DateUtility.parseXSDDateTime(s4);
        String s5 = "0000-01-01T00:00:00Z";
        DateTime dt5 = DateUtility.parseXSDDateTime(s5);
        assertTrue(dt4.isEqual(dt5));
    }

    @Test
    public void testMillis() throws Exception {
        String lex1 = "1999-12-31T22:33:44.500Z";
        String can1 = "1999-12-31T22:33:44.5Z";
        DateTime dt1 = DateUtility.parseXSDDateTime(lex1);
        assertEquals(can1, DateUtility.getXSDDateTime(dt1));

        String lex2 = "1999-12-31T22:33:44.050Z";
        String can2 = "1999-12-31T22:33:44.05Z";
        DateTime dt2 = DateUtility.parseXSDDateTime(lex2);
        assertEquals(can2, DateUtility.getXSDDateTime(dt2));

        String lex3 = "1999-12-31T22:33:44Z";
        String can3 = lex3;
        DateTime dt3 = DateUtility.parseXSDDateTime(lex3);
        assertEquals(can3, DateUtility.getXSDDateTime(dt3));

        String lex4 = "2010-06-01T04:52:14.025Z";
        String can4 = lex4;
        DateTime dt4 = DateUtility.parseXSDDateTime(lex4);
        assertEquals(can4, DateUtility.getXSDDateTime(dt4));

        String lex5 = "2010-06-01T02:52:14.25Z";
        String can5 = "2010-06-01T02:52:14.25Z";
        DateTime dt5 = DateUtility.parseXSDDateTime(lex5);
        assertEquals(can5, DateUtility.getXSDDateTime(dt5));
    }

    @Test
    public void testNegativeDates() throws Exception {
        String lex1 = "-0003-12-13T22:33:44.010Z";
        String can1 = "-0003-12-13T22:33:44.01Z";
        DateTime dt1 = DateUtility.parseXSDDateTime(lex1);
        assertEquals(can1, DateUtility.getXSDDateTime(dt1));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testIncorrectDateFormats() throws Exception {
        String[] badDates = {"-985-12-13T22:33:44.000Z",
                             "-3-12-13T22:33:44.01Z",
                             "100-12-13T22:33:44.01Z",
                             "1970-01",
                             "1970-1-1",
                             "12345-01-01T00:00:00.",
                             "",
                             "AAAA-BB-CCTDD:EE:FF:GG.HHH"
        };

        for (String badDate : badDates) {
            DateUtility.parseXSDDateTime(badDate);
        }
    }
}
