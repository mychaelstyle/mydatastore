/**
 * AWS provider abstract class
 * @author Masanori Nakashima
 */
package com.mychaelstyle.mydatastore.providers;

import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.mychaelstyle.mydatastore.Provider;

/**
 * AWS provider abstract class
 * @author Masanori Nakashima
 */
public abstract class AWS extends Provider {
    /**
     * configuration map key for Amazon Web Service Access Key
     */
    public static final String CONFIG_ACCESS_KEY = "aws_access_key";
    /**
     * configuration map key for Amazon Web Service Access Secret Key
     */
    public static final String CONFIG_SECRET_KEY = "aws_secret_key";
    /**
     * get AWSCredentials instance for connection
     * @return AWSCredentials
     */
    protected static AWSCredentials getCredentials(Map<String,String> config) throws Exception {
        String accessKey = config.get(AWS.CONFIG_ACCESS_KEY);
        String secretKey = config.get(AWS.CONFIG_SECRET_KEY);
        if(accessKey==null) throw new Exception("require " + AWS.CONFIG_ACCESS_KEY);
        if(secretKey==null) throw new Exception("require " + AWS.CONFIG_SECRET_KEY);
        return new BasicAWSCredentials(accessKey,secretKey);
    }

}
