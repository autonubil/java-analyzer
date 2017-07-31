package com.autonubil.analyzer.cli;

import java.util.List;
import java.util.Map;

import com.autonubil.analyzer.dependencies.DependencyAnalyser;

public class AnalyzerCli {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		DependencyAnalyser depAnalyzer = new DependencyAnalyser();
		Map<String,Object> options = depAnalyzer.getDefaultOptions();
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/apache/commons/commons-compress/1.9/commons-compress-1.9.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/apache/commons/commons-lang3/3.5/commons-lang3-3.5.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/amazonaws/aws-java-sdk-core/1.9.6/aws-java-sdk-core-1.9.6.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/amazonaws/aws-java-sdk-sts/1.9.6/aws-java-sdk-sts-1.9.6.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/slf4j/slf4j-api/1.7.7/slf4j-api-1.7.7.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.25/jcl-over-slf4j-1.7.25.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/slf4j/log4j-over-slf4j/1.7.25/log4j-over-slf4j-1.7.25.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/slf4j/jul-to-slf4j/1.7.25/jul-to-slf4j-1.7.25.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/thymeleaf/thymeleaf-spring4/2.1.5.RELEASE/thymeleaf-spring4-2.1.5.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/thymeleaf/thymeleaf/2.1.5.RELEASE/thymeleaf-2.1.5.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/apache/httpcomponents/httpclient/4.3/httpclient-4.3.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/apache/httpcomponents/httpcore/4.3/httpcore-4.3.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/google/protobuf/protobuf-java/2.5.0/protobuf-java-2.5.0.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/ch/qos/logback/logback-classic/1.1.11/logback-classic-1.1.11.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/ch/qos/logback/logback-core/1.1.11/logback-core-1.1.11.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/hibernate/hibernate-validator/5.2.5.Final/hibernate-validator-5.2.5.Final.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/unbescape/unbescape/1.1.0.RELEASE/unbescape-1.1.0.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.8.4/jackson-annotations-2.8.4.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.8.4/jackson-databind-2.8.4.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.8.4/jackson-core-2.8.4.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/apache/tomcat/embed/tomcat-embed-websocket/8.5.13/tomcat-embed-websocket-8.5.13.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/apache/tomcat/embed/tomcat-embed-el/8.5.13/tomcat-embed-el-8.5.13.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/8.5.13/tomcat-embed-core-8.5.13.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/postgresql/postgresql/42.0.0/postgresql-42.0.0.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/commons-io/commons-io/2.5/commons-io-2.5.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/commons-lang/commons-lang/2.4/commons-lang-2.4.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/spring-beans/4.3.8.RELEASE/spring-beans-4.3.8.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/spring-core/4.3.8.RELEASE/spring-core-4.3.8.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/spring-jdbc/4.3.8.RELEASE/spring-jdbc-4.3.8.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/spring-webmvc/4.3.8.RELEASE/spring-webmvc-4.3.8.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/spring-tx/4.3.8.RELEASE/spring-tx-4.3.8.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/spring-expression/4.3.8.RELEASE/spring-expression-4.3.8.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/spring-aop/4.3.8.RELEASE/spring-aop-4.3.8.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/spring-web/4.3.8.RELEASE/spring-web-4.3.8.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/spring-context/4.3.8.RELEASE/spring-context-4.3.8.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/bettercloud/vault-java-driver/2.0.0/vault-java-driver-2.0.0.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/codehaus/groovy/groovy/2.4.3/groovy-2.4.3.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/bouncycastle/bcpkix-jdk15on/1.57/bcpkix-jdk15on-1.57.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/bouncycastle/bcprov-jdk16/1.46/bcprov-jdk16-1.46.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/bouncycastle/bcprov-jdk15on/1.57/bcprov-jdk15on-1.57.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/aopalliance/aopalliance/1.0/aopalliance-1.0.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/javax/activation/activation/1.1/activation-1.1.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/flywaydb/flyway-core/4.0.3/flyway-core-4.0.3.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/ognl/ognl/3.0.8/ognl-3.0.8.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/joda-time/joda-time/2.9.9/joda-time-2.9.9.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/net/minidev/json-smart/2.2.1/json-smart-2.2.1.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/net/minidev/accessors-smart/1.1/accessors-smart-1.1.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/io/github/lukehutch/fast-classpath-scanner/2.0.19/fast-classpath-scanner-2.0.19.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/security/spring-security-core/4.1.4.RELEASE/spring-security-core-4.1.4.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-localauth/0.1.41/an-identity-localauth-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-app/0.1.41/an-identity-app-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-util/0.1.41/an-identity-util-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-auth-ui/0.1.41/an-identity-auth-ui-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ldap-api/0.1.41/an-identity-ldap-api-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-mail-ui/0.1.41/an-identity-mail-ui-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ldapotp-api/0.1.41/an-identity-ldapotp-api-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-persistence-impl/0.1.41/an-identity-persistence-impl-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ldap-ui/0.1.41/an-identity-ldap-ui-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-mail-impl/0.1.41/an-identity-mail-impl-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-certs-api/0.1.41/an-identity-certs-api-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ldapusers-api/0.1.41/an-identity-ldapusers-api-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ui/0.1.41/an-identity-ui-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-apps-ui/0.1.41/an-identity-apps-ui-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-mail-api/0.1.41/an-identity-mail-api-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-audit-impl/0.1.41/an-identity-audit-impl-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ovpn-api/0.1.41/an-identity-ovpn-api-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-certs-impl-acme/0.1.41/an-identity-certs-impl-acme-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ldap-impl/0.1.41/an-identity-ldap-impl-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-audit-api/0.1.41/an-identity-audit-api-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-persistence-api/0.1.41/an-identity-persistence-api-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-auth-api/0.1.41/an-identity-auth-api-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ldapotp-impl/0.1.41/an-identity-ldapotp-impl-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-secrets-api/0.1.41/an-identity-secrets-api-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-apps-impl/0.1.41/an-identity-apps-impl-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-less/0.1.41/an-identity-less-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-openid-api/0.1.41/an-identity-openid-api-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-openid-ui/0.1.41/an-identity-openid-ui-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-secrets-impl/0.1.41/an-identity-secrets-impl-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ovpn-common/0.1.41/an-identity-ovpn-common-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-apps-api/0.1.41/an-identity-apps-api-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-db-common/0.1.41/an-identity-db-common-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-openid-impl/0.1.41/an-identity-openid-impl-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-me-ui/0.1.41/an-identity-me-ui-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ovpn-ui/0.1.41/an-identity-ovpn-ui-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-aws-impl/0.1.41/an-identity-aws-impl-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-auth-impl/0.1.41/an-identity-auth-impl-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ldapusers-impl/0.1.41/an-identity-ldapusers-impl-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ldapotp-ui/0.1.41/an-identity-ldapotp-ui-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ovpn-vault/0.1.41/an-identity-ovpn-vault-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-db-pgsql/0.1.41/an-identity-db-pgsql-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/autonubil/identity/an-identity-ldapusers-ui/0.1.41/an-identity-ldapusers-ui-0.1.41.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/apache/tomcat/tomcat-juli/8.5.11/tomcat-juli-8.5.11.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/apache/tomcat/tomcat-dbcp/8.5.11/tomcat-dbcp-8.5.11.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/apache/velocity/velocity/1.7/velocity-1.7.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/antlr/antlr-runtime/3.5.2/antlr-runtime-3.5.2.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/fasterxml/classmate/1.1.0/classmate-1.1.0.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/io/jsonwebtoken/jjwt/0.7.0/jjwt-0.7.0.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/shredzone/acme4j/acme4j-client/0.10/acme4j-client-0.10.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/javassist/javassist/3.16.1-GA/javassist-3.16.1-GA.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/google/code/gson/gson/2.5/gson-2.5.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/unboundid/unboundid-ldapsdk/3.2.1/unboundid-ldapsdk-3.2.1.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/webjars/webjars-locator-core/0.32/webjars-locator-core-0.32.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/webjars/webjars-locator/0.32-1/webjars-locator-0.32-1.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/commons-codec/commons-codec/1.10/commons-codec-1.10.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/sun/mail/javax.mail/1.5.6/javax.mail-1.5.6.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/sun/mail/smtp/1.5.6/smtp-1.5.6.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/ow2/asm/asm/5.0.3/asm-5.0.3.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/github/sommeri/less4j/1.17.2/less4j-1.17.2.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/yaml/snakeyaml/1.17/snakeyaml-1.17.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/nz/net/ultraq/thymeleaf/thymeleaf-layout-dialect/1.4.0/thymeleaf-layout-dialect-1.4.0.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/commons-beanutils/commons-beanutils/1.8.3/commons-beanutils-1.8.3.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/bitbucket/b_c/jose4j/0.5.5/jose4j-0.5.5.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/boot/spring-boot-starter-web/1.4.6.RELEASE/spring-boot-starter-web-1.4.6.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/boot/spring-boot-starter/1.4.6.RELEASE/spring-boot-starter-1.4.6.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/1.4.6.RELEASE/spring-boot-autoconfigure-1.4.6.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/boot/spring-boot-starter-tomcat/1.4.6.RELEASE/spring-boot-starter-tomcat-1.4.6.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/boot/spring-boot-starter-thymeleaf/1.4.6.RELEASE/spring-boot-starter-thymeleaf-1.4.6.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/boot/spring-boot/1.4.6.RELEASE/spring-boot-1.4.6.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/springframework/boot/spring-boot-starter-logging/1.4.6.RELEASE/spring-boot-starter-logging-1.4.6.RELEASE.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/google/zxing/core/3.2.1/core-3.2.1.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/google/zxing/javase/3.2.1/javase-3.2.1.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/beust/jcommander/1.48/jcommander-1.48.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/jayway/jsonpath/json-path/2.2.0/json-path-2.2.0.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/com/auth0/java-jwt/3.2.0/java-jwt-3.2.0.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/de/disk0/simple-sql-builder/0.1.22/simple-sql-builder-0.1.22.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/org/jboss/logging/jboss-logging/3.2.1.Final/jboss-logging-3.2.1.Final.jar");
		((List<String>) options.get("sources")).add("/Users/czeumer/.m2/repository/javax/validation/validation-api/1.1.0.Final/validation-api-1.1.0.Final.jar");
 		
		try {
			depAnalyzer.analyze(options);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
