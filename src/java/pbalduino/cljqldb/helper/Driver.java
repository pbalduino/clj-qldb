package pbalduino.cljqldb.helper;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.qldbsession.AmazonQLDBSessionClientBuilder;

import software.amazon.qldb.exceptions.QldbClientException;
import software.amazon.qldb.PooledQldbDriver;
import software.amazon.qldb.QldbDriver;
import software.amazon.qldb.QldbSession;

public final class Driver {
  private Driver() {}

  private static String obfuscate(String value) {
    char[] chars = value.toCharArray();

    for(int p = 3; p < chars.length - 1; p++) {
      chars[p] = '*';
    }

    return new String(chars);
  }

  public static PooledQldbDriver createQldbDriver(final String ledgerName, final int retries) {
    AmazonQLDBSessionClientBuilder builder = AmazonQLDBSessionClientBuilder.standard();

    DefaultAWSCredentialsProviderChain awsCredentialsProvider = DefaultAWSCredentialsProviderChain.getInstance();
    AWSCredentials credentials = awsCredentialsProvider.getCredentials();

    builder.setCredentials(awsCredentialsProvider);

    System.out.println("[clj-qldb] Logging builder: " + builder);
    System.out.println("[clj-qldb] Logging provider: " + awsCredentialsProvider);
    System.out.println("[clj-qldb] Logging credential impl: " + credentials.getClass());
    System.out.println("[clj-qldb] Logging credentials: " + obfuscate(credentials.getAWSAccessKeyId()) + ":" + obfuscate(credentials.getAWSSecretKey()));

    PooledQldbDriver driver = PooledQldbDriver.builder()
            .withLedger(ledgerName)
            .withRetryLimit(retries)
            .withSessionClientBuilder(builder)
            .build();

    System.out.println("[clj-qldb] Logging driver: " + driver);

    return driver;
  }
}
