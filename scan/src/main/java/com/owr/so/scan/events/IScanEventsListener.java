package com.owr.so.scan.events;

/**
 * @author Paulius Zubavicius
 *
 */

public interface IScanEventsListener {

	void event(ScanEvent event, Object... data);

}
