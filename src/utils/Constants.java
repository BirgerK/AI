package utils;

public abstract class Constants {
	public static final int MPS_DISPATCHER_PORT = 50000;
	public static final int START_SERVER_SERVICE_PORT = 50005;
	public static final int MPS_SERVER_THREAD_PORT = 50010;
	
	
	public static final String CMD_ERSTELLE_KUNDENAUFTRAG = "erstelleKundenAuftrag";
	public static final String CMD_BERECHNE_FERTIGUNGSZEIT = "berechneFertigungszeitpunkt";
	public static final String CMD_ERSTELLE_FERTIGUNGSAUFTRAG = "erstelleFertigungsauftrag";
	public static final String CMD_ERSTELLE_TRANSPORTAUFTRAG = "erstelleTransportauftrag";
	public static final String CMD_BERECHNE_KOSTEN = "berechneKosten";
	public static final String CMD_ERSTELLE_ANGEBOT = "erstelleAngebot";
	public static final String CMD_PING = "PING";
	public static final String CMD_PONG = "PONG";
	
	public static final String CMD_START_SERVER = "StartServer";
	public static final String CMD_STOP_SERVER = "StopServer";
	public static final String ANSWER_DONE = "DONE";
	
	public static final String CMD_ADD_SERVER = "FuegeServerZuDispatcher";
	
	public static final String STATUS_OFFLINE = "Offline";
	public static final String STATUS_BUSY = "Busy";
	public static final String STATUS_IDLE = "Idle";
}
