//package com.alibou.security.config;
//
//import com.google.api.client.auth.oauth2.TokenResponse;
//import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
//import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
//import com.google.api.client.http.GenericUrl;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//
//public class EmailConfig {
//
//    private static final String TOKEN_SERVER_URL = "https://oauth2.googleapis.com/token";
//
//    public static String getAccessToken(String clientId, String clientSecret, String refreshToken) throws Exception {
//        GoogleTokenResponse response = new GoogleRefreshTokenRequest(
//                new NetHttpTransport(),
//                JacksonFactory.getDefaultInstance(),
//                refreshToken,
//                clientId,
//                clientSecret
//        ).setTokenServerUrl(new GenericUrl(TOKEN_SERVER_URL)).execute();
//
//        return response.getAccessToken();
//    }
//
//    public static String generateXOAuth2Token(String email, String accessToken) {
//        String format = "user=%s\u0001auth=Bearer %s\u0001\u0001";
//        String authString = String.format(format, email, accessToken);
//        return java.util.Base64.getEncoder().encodeToString(authString.getBytes());
//    }
//}


package com.alibou.security.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class EmailConfig {

    private static final String TOKEN_SERVER_URL = "https://oauth2.googleapis.com/token";

    public static String getAccessToken(String clientId, String clientSecret, String refreshToken) throws Exception {
        GoogleTokenResponse response = new GoogleRefreshTokenRequest(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                refreshToken,
                clientId,
                clientSecret
        ).setTokenServerUrl(new GenericUrl(TOKEN_SERVER_URL)).execute();

        return response.getAccessToken();
    }

    public static String generateXOAuth2Token(String email, String accessToken) {
        String format = "user=%s\u0001auth=Bearer %s\u0001\u0001";
        String authString = String.format(format, email, accessToken);
        return java.util.Base64.getEncoder().encodeToString(authString.getBytes());
    }
}
