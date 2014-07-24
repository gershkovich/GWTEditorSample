package pathology.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

/**
 * Created by pg86 on 7/24/14.
 */
@RemoteServiceRelativePath( "EditorService" )
public interface EditorService extends RemoteService
{

    String submitRTF(String html);

    /**
     * Utility/Convenience class.
     * Use EditorService.App.getInstance() to access static instance of EditorServiceAsync
     */
    public static class App
    {

        private static final EditorServiceAsync ourInstance = ( EditorServiceAsync ) GWT.create(EditorService.class);

        public static EditorServiceAsync getInstance()
        {

            return ourInstance;
        }
    }
}
