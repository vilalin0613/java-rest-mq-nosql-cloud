package com.example.vila.repository.cassandra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.WriteType;
import com.datastax.driver.core.exceptions.DriverException;
import com.datastax.driver.core.policies.RetryPolicy;
import com.google.common.annotations.VisibleForTesting;

public class LoggingRetryPolicy implements RetryPolicy {

	private static final Logger logger = LoggerFactory.getLogger(LoggingRetryPolicy.class);
	private static final Logger briefLogger = LoggerFactory.getLogger("briefLogger");

	@VisibleForTesting
	static final String IGNORING_READ_TIMEOUT = "Ignoring onReadTimeout (initial consistency: {}, required responses: {}, received responses: {}, data retrieved: {}, retries: {})";

	@VisibleForTesting
	static final String RETRYING_ON_READ_TIMEOUT = "Retrying onReadTimeout on {} at consistency {} (initial consistency: {}, required responses: {}, received responses: {}, data retrieved: {}, retries: {})";

	@VisibleForTesting
	static final String RETHROW_READ_TIMEOUT = "Throw onReadTimeout on {} at consistency {} (initial consistency: {}, required responses: {}, received responses: {}, data retrieved: {}, retries: {})";
	@VisibleForTesting
	static final String IGNORING_WRITE_TIMEOUT = "Ignoring onWriteTimeout (initial consistency: {}, write type: {}, required acknowledgments: {}, received acknowledgments: {}, retries: {})";

	@VisibleForTesting
	static final String RETRYING_ON_WRITE_TIMEOUT = "Retrying onWriteTimeout on {} at consistency {} (initial consistency: {}, write type: {}, required acknowledgments: {}, received acknowledgments: {}, retries: {})";

	@VisibleForTesting
	static final String RETHROW_WRITE_TIMEOUT = "Throw onWriteTimeout on {} at consistency {} (initial consistency: {}, write type: {}, required acknowledgments: {}, received acknowledgments: {}, retries: {})";

	@VisibleForTesting
	static final String IGNORING_UNAVAILABLE = "Ignoring onUnavailable exception (initial consistency: {}, required replica: {}, alive replica: {}, retries: {})";

	@VisibleForTesting
	static final String RETRYING_ON_UNAVAILABLE = "Retrying onUnavailable exception on {} at consistency {} (initial consistency: {}, required replica: {}, alive replica: {}, retries: {})";

	@VisibleForTesting
	static final String RETHROW_UNAVAILABLE = "Throw onUnavailable exception on {} at consistency {} (initial consistency: {}, required replica: {}, alive replica: {}, retries: {})";

	@VisibleForTesting
	static final String IGNORING_REQUEST_ERROR = "Ignoring onRequestError (initial consistency: {}, retries: {}, exception: {})";

	@VisibleForTesting
	static final String RETRYING_ON_REQUEST_ERROR = "Retrying on onRequestError {} at consistency {} (initial consistency: {}, retries: {}, exception: {})";

	@VisibleForTesting
	static final String RETHROW_REQUEST_ERROR = "Throw onRequestError {} at consistency {} (initial consistency: {}, retries: {}, exception: {})";

	private final RetryPolicy policy;

	public LoggingRetryPolicy(RetryPolicy policy) {
		this.policy = policy;
	}

	private static ConsistencyLevel cl(ConsistencyLevel cl, RetryDecision decision) {
		return decision.getRetryConsistencyLevel() == null ? cl : decision.getRetryConsistencyLevel();
	}

	private static String host(RetryDecision decision) {
		return decision.isRetryCurrent() ? "same host" : "next host";
	}

	@Override
	public RetryDecision onReadTimeout(Statement statement, ConsistencyLevel cl, int requiredResponses,
			int receivedResponses, boolean dataRetrieved, int nbRetry) {
		RetryDecision decision = policy.onReadTimeout(statement, cl, requiredResponses, receivedResponses,
				dataRetrieved, nbRetry);
		switch (decision.getType()) {
			case IGNORE:
				logDecision(IGNORING_READ_TIMEOUT, cl, requiredResponses, receivedResponses, dataRetrieved, nbRetry);
				briefLogDecision(IGNORING_READ_TIMEOUT, cl, requiredResponses, receivedResponses, dataRetrieved, nbRetry);
				break;
			case RETRY:
				logDecision(RETRYING_ON_READ_TIMEOUT, host(decision), cl(cl, decision), cl, requiredResponses,
						receivedResponses, dataRetrieved, nbRetry);
				briefLogDecision(RETRYING_ON_READ_TIMEOUT, host(decision), cl(cl, decision), cl, requiredResponses,
						receivedResponses, dataRetrieved, nbRetry);
				break;
			case RETHROW:
				logDecision(RETHROW_READ_TIMEOUT, host(decision), cl(cl, decision), cl, requiredResponses,
						receivedResponses, dataRetrieved, nbRetry);
				briefLogDecision(RETHROW_READ_TIMEOUT, host(decision), cl(cl, decision), cl, requiredResponses,
						receivedResponses, dataRetrieved, nbRetry);
				break;
		}
		return decision;
	}

	@Override
	public RetryDecision onWriteTimeout(Statement statement, ConsistencyLevel cl, WriteType writeType, int requiredAcks,
			int receivedAcks, int nbRetry) {
		RetryDecision decision = policy.onWriteTimeout(statement, cl, writeType, requiredAcks, receivedAcks, nbRetry);
		switch (decision.getType()) {
			case IGNORE:
				logDecision(IGNORING_WRITE_TIMEOUT, cl, writeType, requiredAcks, receivedAcks, nbRetry);
				briefLogDecision(IGNORING_WRITE_TIMEOUT, cl, writeType, requiredAcks, receivedAcks, nbRetry);
				break;
			case RETRY:
				logDecision(RETRYING_ON_WRITE_TIMEOUT, host(decision), cl(cl, decision), cl, writeType, requiredAcks,
						receivedAcks, nbRetry);
				briefLogDecision(RETRYING_ON_WRITE_TIMEOUT, host(decision), cl(cl, decision), cl, writeType, requiredAcks,
						receivedAcks, nbRetry);
				break;
			case RETHROW:
				logDecision(RETHROW_WRITE_TIMEOUT, host(decision), cl(cl, decision), cl, writeType, requiredAcks,
						receivedAcks, nbRetry);
				briefLogDecision(RETHROW_WRITE_TIMEOUT, host(decision), cl(cl, decision), cl, writeType, requiredAcks,
						receivedAcks, nbRetry);
				break;
		}
		return decision;
	}

	@Override
	public RetryDecision onUnavailable(Statement statement, ConsistencyLevel cl, int requiredReplica, int aliveReplica,
			int nbRetry) {
		RetryDecision decision = policy.onUnavailable(statement, cl, requiredReplica, aliveReplica, nbRetry);
		switch (decision.getType()) {
			case IGNORE:
				logDecision(IGNORING_UNAVAILABLE, cl, requiredReplica, aliveReplica, nbRetry);
				briefLogDecision(IGNORING_UNAVAILABLE, cl, requiredReplica, aliveReplica, nbRetry);
				break;
			case RETRY:
				logDecision(RETRYING_ON_UNAVAILABLE, host(decision), cl(cl, decision), cl, requiredReplica, aliveReplica,
						nbRetry);
				briefLogDecision(RETRYING_ON_UNAVAILABLE, host(decision), cl(cl, decision), cl, requiredReplica, aliveReplica,
						nbRetry);
				break;
			case RETHROW:
				logDecision(RETHROW_UNAVAILABLE, host(decision), cl(cl, decision), cl, requiredReplica, aliveReplica,
						nbRetry);
				briefLogDecision(RETHROW_UNAVAILABLE, host(decision), cl(cl, decision), cl, requiredReplica, aliveReplica,
						nbRetry);
				break;
		}
		return decision;
	}

	@Override
	public RetryDecision onRequestError(Statement statement, ConsistencyLevel cl, DriverException e, int nbRetry) {
		RetryDecision decision = policy.onRequestError(statement, cl, e, nbRetry);
		switch (decision.getType()) {
			case IGNORE:
				logDecision(IGNORING_REQUEST_ERROR, cl, nbRetry, e);
				briefLogDecision(IGNORING_REQUEST_ERROR, cl, nbRetry, e.getMessage());
				break;
			case RETRY:
				logDecision(RETRYING_ON_REQUEST_ERROR, host(decision), cl(cl, decision), cl, nbRetry, e);
				briefLogDecision(RETRYING_ON_REQUEST_ERROR, host(decision), cl(cl, decision), cl, nbRetry, e.getMessage());
				break;
			case RETHROW:
				logDecision(RETHROW_REQUEST_ERROR, host(decision), cl(cl, decision), cl, nbRetry, e);
				briefLogDecision(RETHROW_REQUEST_ERROR, host(decision), cl(cl, decision), cl, nbRetry, e.getMessage());
				break;
		}
		return decision;
	}

	@Override
	public void init(Cluster cluster) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	/**
	 * Logs the decision according to the given template and parameters. The log
	 * level is INFO, but subclasses may override.
	 *
	 * @param template
	 *            The template to use; arguments must be specified in SLF4J
	 *            style, i.e. {@code "{}"}.
	 * @param parameters
	 *            The template parameters.
	 */
	protected void logDecision(String template, Object... parameters) {
		logger.info(template, parameters);
	}
	
	/**
	 * Logs the decision according to the given template and parameters. The log
	 * level is INFO, but subclasses may override.
	 *
	 * @param template
	 *            The template to use; arguments must be specified in SLF4J
	 *            style, i.e. {@code "{}"}.
	 * @param parameters
	 *            The template parameters.
	 */
	protected void briefLogDecision(String template, Object... parameters) {
		briefLogger.info(template, parameters);
	}
}
