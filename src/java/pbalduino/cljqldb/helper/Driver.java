package pbalduino.cljqldb.helper;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.qldbsession.AmazonQLDBSessionClientBuilder;

import software.amazon.qldb.exceptions.QldbClientException;
import software.amazon.qldb.PooledQldbDriver;
import software.amazon.qldb.QldbDriver;
import software.amazon.qldb.QldbSession;

public final class Driver {
  private Driver() {}

  public static PooledQldbDriver createQldbDriver(final String ledgerName, final int retries) {
    AmazonQLDBSessionClientBuilder builder = AmazonQLDBSessionClientBuilder.standard();

    builder.setCredentials(DefaultAWSCredentialsProviderChain.getInstance());

    return PooledQldbDriver.builder()
            .withLedger(ledgerName)
            .withRetryLimit(retries)
            .withSessionClientBuilder(builder)
            .build();
  }
}
