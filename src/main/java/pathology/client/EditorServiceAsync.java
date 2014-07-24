package pathology.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by pg86 on 7/24/14.
 */
public interface EditorServiceAsync
{

    void submitRTF(String html, AsyncCallback<String> submitRichTextAsyncCallback);
}
