package cmput301t4.gameswap.Managers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import cmput301t4.gameswap.Exceptions.ServerDownException;
import cmput301t4.gameswap.Models.ImageModel;
import cmput301t4.gameswap.Models.User;
import cmput301t4.gameswap.serverTools.ElasticSearchResponse;
import cmput301t4.gameswap.serverTools.ElasticSearchSearchResponse;

/**
 * Controller that houses all functions related
 * to the server
 */
public class ServerManager {

    private static boolean foundResult = Boolean.FALSE;
    private static boolean serverDown = Boolean.FALSE;

    private static HttpClient httpclient;
    private static Gson gson = new Gson();

    private static final String baseURL = "http://cmput301.softwareprocess.es:8080/cmput301f15t04/";

    /**
     * Get the user from with the username given
     * and set the User to it.
     *
     * @param username
     */
    public static void getUserOnline(final String username){
        if(!serverDown) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //Taken from https://github.com/rayzhangcl/ESDemo
                        httpclient = new DefaultHttpClient(new BasicHttpParams());
                        HttpGet getRequest = new HttpGet(baseURL + "users/" + username + "/_source");
                        getRequest.addHeader("Accept", "application/json");
                        HttpResponse response = httpclient.execute(getRequest);

                        String json = getEntityContent(response);

                        // We have to tell GSON what type we expect
                        //Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<User>>() {
                        //}.getType();
                        // Now we expect to get a Recipe response
                        //ElasticSearchResponse<User> esResponse = gson.fromJson(json, elasticSearchResponseType);

                        //specify to gson what type of data is being read
                        User user = gson.fromJson(json, User.class);

                        //UserManager.setTrader(esResponse.getSource());
                        UserManager.setTrader(user);
                    } catch (IOException e) {
                        throw new RuntimeException("ServerManager.getUserOnline failed");
                    }
                }
            };

            Thread serverThread = new Thread(runnable);
            serverThread.start();

            try {
                serverThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        } else {
            throw new ServerDownException();
        }
    }

    /**
     * Adapted from https://github.com/rayzhangcl/ESDemo on November 20, 2015
     *
     * @param username
     * @return
     */
    public static void searchForUser(final String username) {
        if(!serverDown) {
            Thread serverThread = new Thread(new Runnable() {

                @Override
                public void run () {
                    try {
                        //this url request will ignore all the data of each id inside user
                        httpclient = new DefaultHttpClient(new BasicHttpParams());
                        HttpGet searchRequest = new HttpGet(baseURL + "users/_search?pretty=1&q=" + username + "&_source=false");
                        searchRequest.setHeader("Accept", "application/json");
                        HttpResponse response = httpclient.execute(searchRequest);

                        String json = getEntityContent(response);

                        Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<User>>() {
                        }.getType();
                        ElasticSearchSearchResponse<User> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);

                        if (esResponse.getHits().size() != 0) {
                            ServerManager.resultFound();
                        } else {
                            ServerManager.resultNotFound();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("ServerManager.searchForUser failed");
                    }
                }
            });

            serverThread.start();

            try {
                serverThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("serverThread.join failed");
            }
        } else {
            throw new ServerDownException();
        }
    }

    private static void resultFound() {foundResult = Boolean.TRUE;}

    private static void resultNotFound() {foundResult = Boolean.FALSE;}

    public static boolean checkResult() {return foundResult;}

    public static void serverNotDown(){serverDown = Boolean.FALSE;}

    public static void serverIsDown(){serverDown = Boolean.TRUE;}

    /**
     * checks the status of the server to see if it's up
     * @return a boolean representing the state of the server
     */
    public static boolean checkServerStatus(){
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpGet searchRequest = new HttpGet(baseURL + "_search?pretty=1");
                searchRequest.setHeader("Accept", "application/json");
                HttpResponse response = null;

                try {
                    response = httpclient.execute(searchRequest);
                } catch (IOException e) {
                    serverIsDown();
                    return;
                }

                if(response.getStatusLine().getStatusCode() < 203) {
                    serverNotDown();
                } else {
                    serverIsDown();
                }
            }
        });

        serverThread.start();

        try {
            serverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("serverThread.join failed");
        }

        return serverDown;
    }

    /**
     * Savesuser into server
     * @param user User object that is beng saved
     */
    public static void saveUserOnline(final User user){
        if(!serverDown) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    httpclient = new DefaultHttpClient(new BasicHttpParams());
                    HttpPost httpPost = new HttpPost(baseURL + "users/" + user.getUserName());
                    StringEntity stringentity = null;

                    try {
                        stringentity = new StringEntity(gson.toJson(user));
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setEntity(stringentity);

                    try {
                        httpclient.execute(httpPost);
                    } catch (IOException e) {
                        throw new RuntimeException("Post request failed");
                    }
                }
            };

            Thread serverThread = new Thread(runnable);
            serverThread.start();

            try {
                serverThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        } else {
            throw new ServerDownException();
        }


    }

    /**
     * get the http response and return json string
     * Taken from https://github.com/rayzhangcl/ESDemo on November 20, 2015
     */
    private static String getEntityContent(HttpResponse response) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));
        String output;
        System.err.println("Output from Server -> ");
        String json = "";
        while ((output = br.readLine()) != null) {
            System.err.println(output);
            json += output;
        }
        System.err.println("JSON:" + json);
        return json;
    }

    /**
     * Locate and load the trader that the User is interacting with
     * @param username the username of the trader that is currenty being interacted with
     */
    public static void getFriendOnline(final String username){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //taken from http://stackoverflow.com/questions/693997/how-to-set-httpresponse-timeout-for-android-in-java
                HttpParams httpParameters = new BasicHttpParams();
                // Set the timeout in milliseconds until a connection is established.
                // The default value is zero, that means the timeout is not used.
                int timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                // Set the default socket timeout (SO_TIMEOUT)
                // in milliseconds which is the timeout for waiting for data.
                int timeoutSocket = 5000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                String url = "http://cmput301.softwareprocess.es:8080/cmput301f15t04/users/" + username + "/_source";
                System.out.println(url);
                HttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpGet httpGet = new HttpGet(url);
                HttpResponse response = null;

                try {                           //run URL
                    response = httpClient.execute(httpGet);
                } catch (ClientProtocolException e1) {
                    throw new RuntimeException(e1);
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
                BufferedReader rd = null;
                User sr = null;
                Gson gson = new Gson();

                try {
                    rd = new BufferedReader((new InputStreamReader((response.getEntity().getContent()))));
                    //String line = rd.readLine();
                    //System.out.println(line);
                    sr = gson.fromJson(rd, User.class);
                } catch (JsonIOException e) {
                    throw new RuntimeException(e);
                } catch (JsonSyntaxException e) {
                    throw new RuntimeException(e);
                } catch (IllegalStateException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                UserManager.setFriend(sr);
            }
        };
        Thread serverThread = new Thread(runnable);
        serverThread.start();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }

    }//end getUserOnline

    public static void deleteUserOnline(final String username){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                HttpParams httpParameters = new BasicHttpParams();
                // Set the timeout in milliseconds until a connection is established.
                // The default value is zero, that means the timeout is not used.
                int timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                // Set the default socket timeout (SO_TIMEOUT)
                // in milliseconds which is the timeout for waiting for data.
                int timeoutSocket = 5000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                String url = "http://cmput301.softwareprocess.es:8080/cmput301f15t04/users/" + username;

                System.out.println(url);
                HttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpDelete httpDel = new HttpDelete(url);
                HttpResponse response = null;

                try {                           //run URL
                    response = httpClient.execute(httpDel);
                } catch (ClientProtocolException e1) {
                    throw new RuntimeException(e1);
                } catch (IOException e1) {
                    throw new RuntimeException();
                }

                String status = response.getStatusLine().toString();
                System.out.println(status);
                HttpEntity entity = response.getEntity();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String output;
                    System.err.println("Output from Server -> ");
                    while ((output = br.readLine()) != null) {
                        System.err.println(output);
                    }
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        };
        Thread serverThread = new Thread(runnable);
        serverThread.start();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }

    }//end Delete User online

    /*public static void blakeLoadItemdImage(final int itemid) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //taken from http://stackoverflow.com/questions/693997/how-to-set-httpresponse-timeout-for-android-in-java
                HttpParams httpParameters = new BasicHttpParams();
                // Set the timeout in milliseconds until a connection is established.
                // The default value is zero, that means the timeout is not used.
                int timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                // Set the default socket timeout (SO_TIMEOUT)
                // in milliseconds which is the timeout for waiting for data.
                int timeoutSocket = 5000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                String url = "http://cmput301.softwareprocess.es:8080/cmput301f15t04/images/" + UserManager.getTrader().getUserName() + itemid + "/_source";

                HttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpGet httpGet = new HttpGet(url);
                HttpResponse response = null;
                Gson gson = new Gson();

                try {                           //run URL
                    response = httpClient.execute(httpGet);
                } catch (ClientProtocolException e1) {
                    throw new RuntimeException(e1);
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }

                Bitmap image = null;

                try {
                    BufferedReader rd = new BufferedReader((new InputStreamReader((response.getEntity().getContent()))));
                    byte[] bytes = gson.fromJson(rd, byte[].class);
                    image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    UserManager.imageRdy = 1;
                } catch (JsonIOException e) {
                    throw new RuntimeException(e);
                } catch (JsonSyntaxException e) {
                    throw new RuntimeException(e);
                } catch (IllegalStateException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }catch(NullPointerException e){//sets image not ready to display
                    UserManager.imageRdy = 0;
                }
                if(UserManager.imageRdy == 1) {
                    UserManager.setImage(image);
                }
                //TODO: Store image in the item
            }
        };

        Thread serverThread = new Thread(runnable);
        serverThread.start();

        try {
            serverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }*/

    /*public static void blakeSaveItemImage(final int itemid, final Bitmap image) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //taken from http://stackoverflow.com/questions/693997/how-to-set-httpresponse-timeout-for-android-in-java
                HttpParams httpParameters = new BasicHttpParams();
                // Set the timeout in milliseconds until a connection is established.
                // The default value is zero, that means the timeout is not used.
                int timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                // Set the default socket timeout (SO_TIMEOUT)
                // in milliseconds which is the timeout for waiting for data.
                int timeoutSocket = 5000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                String url = "http://cmput301.softwareprocess.es:8080/cmput301f15t04/images/" + UserManager.getTrader().getUserName() + itemid;
                HttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost httpPost = new HttpPost(url);
                HttpResponse response = null;

                Gson gson = new Gson();
                StringEntity stringentity = null;

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();

                try {
                    stringentity = new StringEntity(gson.toJson(bytes));

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                httpPost.setHeader("Accept","application/json");
                httpPost.setEntity(stringentity);

                try {                           //run URL
                    response = httpClient.execute(httpPost);//BAD HTTP REQUEST HERE
                } catch (ClientProtocolException e1) {
                    throw new RuntimeException(e1);
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }

                String status = response.getStatusLine().toString();
                System.out.println(status);

                HttpEntity entity = response.getEntity();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String output;
                    System.err.println("Output from Server -> ");
                    while ((output = br.readLine()) != null) {
                        System.err.println(output);
                    }
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        Thread serverThread = new Thread(runnable);
        serverThread.start();

        try {
            serverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }*/

    /**
     * Saves the image of the item onto he server
     * @param image the image that is being saved
     */
    public static void saveImage(final ImageModel image){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //taken from http://stackoverflow.com/questions/693997/how-to-set-httpresponse-timeout-for-android-in-java
                HttpParams httpParameters = new BasicHttpParams();
                // Set the timeout in milliseconds until a connection is established.
                // The default value is zero, that means the timeout is not used.
                int timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                // Set the default socket timeout (SO_TIMEOUT)
                // in milliseconds which is the timeout for waiting for data.
                int timeoutSocket = 5000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                String url = "http://cmput301.softwareprocess.es:8080/cmput301f15t04/images/" + image.getImageuserName() + image.getImageItemId();
                System.out.println(url);
                HttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost httpPost = new HttpPost(url);
                HttpResponse response = null;
                GsonBuilder builder = new GsonBuilder();
                builder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
                Gson gson = builder.create();
                StringEntity stringentity = null;

                try {
                    stringentity = new StringEntity(gson.toJson(image));
                    System.out.println(gson.toJson(image));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                httpPost.setHeader("Accept","application/json");

                httpPost.setEntity(stringentity);

                try {                           //run URL
                    response = httpClient.execute(httpPost);//BAD HTTP REQUEST HERE
                } catch (ClientProtocolException e1) {
                    throw new RuntimeException(e1);
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
                String status = response.getStatusLine().toString();
                System.out.println(status);
                HttpEntity entity = response.getEntity();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String output;
                    System.err.println("Output from Server -> ");
                    while ((output = br.readLine()) != null) {
                        System.err.println(output);
                    }
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        Thread serverThread = new Thread(runnable);
        serverThread.start();

        try {
            serverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }//end save image

    /**
     * loads the image of the item
     * @param item the item that the image belongs to
     */
    public static void loadImage(final int item){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //taken from http://stackoverflow.com/questions/693997/how-to-set-httpresponse-timeout-for-android-in-java
                HttpParams httpParameters = new BasicHttpParams();
                // Set the timeout in milliseconds until a connection is established.
                // The default value is zero, that means the timeout is not used.
                int timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                // Set the default socket timeout (SO_TIMEOUT)
                // in milliseconds which is the timeout for waiting for data.
                int timeoutSocket = 5000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                String url = "http://cmput301.softwareprocess.es:8080/cmput301f15t04/images/" + UserManager.getTrader().getUserName() + item + "/_source";
                GsonBuilder builder = new GsonBuilder();
                builder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);

                HttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpGet httpGet = new HttpGet(url);
                HttpResponse response = null;
                System.out.println(url);
                Gson gson = builder.create();

                try {                           //run URL
                    response = httpClient.execute(httpGet);
                } catch (ClientProtocolException e1) {
                    throw new RuntimeException(e1);
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
                BufferedReader rd = null;
                ImageModel image = null;
                //String jsonData = "";

                try {
                    //String line;
                    rd = new BufferedReader((new InputStreamReader((response.getEntity().getContent()))));
                    image = gson.fromJson(rd, ImageModel.class);
                    System.out.println(image.getImageuserName() + " username for picture");
                    UserManager.imageRdy = 1;
                } catch (JsonIOException e) {
                    throw new RuntimeException(e);
                } catch (JsonSyntaxException e) {
                    throw new RuntimeException(e);
                } catch (IllegalStateException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch(NullPointerException e){//sets image not ready to display
                    UserManager.imageRdy = 0;
                }
                if(UserManager.imageRdy == 1){
                    System.out.println("This is the name of the image taken" + UserManager.getTrader().getUserName() + item);
                    UserManager.setImageModel(image);}
            }
        };

        Thread serverThread = new Thread(runnable);
        serverThread.start();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Delete Image from the server using inputted item id and username
     * @param user The username of the person that the image belongs to
     * @param itemId The id of the item that the image belongs to
     */
    public static void deleteImage(final String user, final int itemId){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                HttpParams httpParameters = new BasicHttpParams();
                // Set the timeout in milliseconds until a connection is established.
                // The default value is zero, that means the timeout is not used.
                int timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                // Set the default socket timeout (SO_TIMEOUT)
                // in milliseconds which is the timeout for waiting for data.
                int timeoutSocket = 5000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                String url = "http://cmput301.softwareprocess.es:8080/cmput301f15t04/images/" + user + itemId;

                System.out.println(url);
                HttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpDelete httpDel = new HttpDelete(url);
                HttpResponse response = null;

                try {                           //run URL
                    response = httpClient.execute(httpDel);
                } catch (ClientProtocolException e1) {
                    throw new RuntimeException(e1);
                } catch (IOException e1) {
                    throw new RuntimeException();
                }

                String status = response.getStatusLine().toString();
                System.out.println(status);
                HttpEntity entity = response.getEntity();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String output;
                    System.err.println("Output from Server -> ");
                    while ((output = br.readLine()) != null) {
                        System.err.println(output);
                    }
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        };
        Thread serverThread = new Thread(runnable);
        serverThread.start();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Notify the other side of the trade what has happened
     * @param type Integer representing what type of action has occured
     */
    public static void notifyTrade(final int type) {
        getFriendOnline(UserManager.getFriend().getUserName());
        switch(type){
            case 0:
                UserManager.getFriend().IncreaseNotifiyAmount(type);
                UserManager.getFriend().getPendingTrades().add(TradeManager.getMostRecentTrade());
                break;
            case 1:
                UserManager.getFriend().IncreaseNotifiyAmount(type);
                break;
            case 2:
                UserManager.getFriend().IncreaseNotifiyAmount(type);
                break;
            case 3:
                UserManager.getFriend().IncreaseNotifiyAmount(type);
        }
        saveUserOnline(UserManager.getFriend());
    }

}//end Server Manager
