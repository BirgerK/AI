package to.mps.managementdashboard;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ESearchWrapper {
	
	Client connToDB;
	String defaultIndexName;
	String defaultDocumentType;
	
	/**
	 * @param dnsServerName Host-Adress of ElasticSearch-Computer in network
	 * @param port port where TransportClient is available, default: 9300
	 * @param indexName in SQL one would call 'database', in MongoDB one would call 'database', fucking ElasticSearch says 'index'
	 * @param documentType in SQL one would call 'table' or 'entity', in MongoDB one would call 'collection', fucking ElasticSearch says 'DocumentType'
	 * @param jsonData an example jsonData for getting the needed mapping (SQL would call it 'columns'). The fields dont need to have legal values, but the keys need to exist and point to any data!
	 */
	public ESearchWrapper(String dnsServerName,int port,String indexName,String documentType,String jsonData){
		final ImmutableSettings.Builder settings = ImmutableSettings.settingsBuilder();
        TransportClient transportClient = new TransportClient(settings);
        transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress(dnsServerName, port));
        connToDB = transportClient;
        defaultIndexName = indexName;
        defaultDocumentType = documentType;
        // MAPPING GOES HERE
 		CreateIndexRequestBuilder createIndexRequestBuilder = connToDB.admin().indices().prepareCreate(defaultIndexName);
 		createIndexRequestBuilder.addMapping(defaultDocumentType, jsonData);
 		
 		// MAPPING DONE
 		createIndexRequestBuilder.execute().actionGet();
	}
	
	/**Add new entry do the ElasticSearch database http://stackoverflow.com/questions/22071198/adding-mapping-to-a-type-from-java-how-do-i-do-it
	 * @param jsonData data which should be added as a JSON-String
	 * @return id in Database of added entry
	 */
	public String addEntry(String jsonData){
		// Add documents
        IndexRequestBuilder indexRequestBuilder = connToDB.prepareIndex(defaultIndexName, defaultDocumentType, null);
        indexRequestBuilder.setSource(jsonData);
        IndexResponse response = indexRequestBuilder.execute().actionGet();
        return response.getIndex();
	}
	
	/**Update an entry with the given data
	 * @param entryId id of entry which should be updated. you get this id by executing the method 'addEntry'
	 * @param jsonUpdateData contains a valid JSON-String, which contains only the fields and values which shall be updated. f.e. database contains '{foo:bar,stuff:no}' and shall be updated to '{foo:bar,stuff:yes}', then this argument must be '{stuff:yes}'
	 */
	public void updateEntry(String entryId, String jsonUpdateData){
		UpdateRequest updateRequest = new UpdateRequest(defaultIndexName,defaultDocumentType,entryId);
		updateRequest.script(jsonUpdateData);
		connToDB.update(updateRequest).actionGet();
	}
}
