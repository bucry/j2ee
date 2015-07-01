package com.oracle.time;

import java.sql.Time;
import java.util.Date;


public interface TimeEjb {
	void setTime(Date init , long interval);
	void check(Time timer);
}
