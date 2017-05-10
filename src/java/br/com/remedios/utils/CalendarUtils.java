
package br.com.remedios.utils;

import java.util.Date;

public class CalendarUtils {
   public static java.sql.Date convertDate(Date date) {
      java.sql.Date dataSql = new java.sql.Date(date.getTime());    
      return dataSql;
    
}
}
