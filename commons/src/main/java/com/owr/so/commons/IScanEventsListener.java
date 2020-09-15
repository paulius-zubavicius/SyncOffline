package com.owr.so.commons;

/**
 * @author Paulius Zubavicius
 *
 */

public interface IScanEventsListener {

	void event(ScanEvent event, Object... data);

}
