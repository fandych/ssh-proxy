//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package vip.iyatou.ssh.proxy.datasource.dbcp;


import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/***
 * copy from apache dbcp2 BasicDataSourceFactory
 *
 * @author chqiang
 */
public class ProxyBasicDataSourceFactory implements ObjectFactory {
    private static final Log log = LogFactory.getLog(ProxyBasicDataSourceFactory.class);
    private static final String PROP_DEFAULTAUTOCOMMIT = "defaultAutoCommit";
    private static final String PROP_DEFAULTREADONLY = "defaultReadOnly";
    private static final String PROP_DEFAULTTRANSACTIONISOLATION = "defaultTransactionIsolation";
    private static final String PROP_DEFAULTCATALOG = "defaultCatalog";
    private static final String PROP_DEFAULTSCHEMA = "defaultSchema";
    private static final String PROP_CACHESTATE = "cacheState";
    private static final String PROP_DRIVERCLASSNAME = "driverClassName";
    private static final String PROP_LIFO = "lifo";
    private static final String PROP_MAXTOTAL = "maxTotal";
    private static final String PROP_MAXIDLE = "maxIdle";
    private static final String PROP_MINIDLE = "minIdle";
    private static final String PROP_INITIALSIZE = "initialSize";
    private static final String PROP_MAXWAITMILLIS = "maxWaitMillis";
    private static final String PROP_TESTONCREATE = "testOnCreate";
    private static final String PROP_TESTONBORROW = "testOnBorrow";
    private static final String PROP_TESTONRETURN = "testOnReturn";
    private static final String PROP_TIMEBETWEENEVICTIONRUNSMILLIS = "timeBetweenEvictionRunsMillis";
    private static final String PROP_NUMTESTSPEREVICTIONRUN = "numTestsPerEvictionRun";
    private static final String PROP_MINEVICTABLEIDLETIMEMILLIS = "minEvictableIdleTimeMillis";
    private static final String PROP_SOFTMINEVICTABLEIDLETIMEMILLIS = "softMinEvictableIdleTimeMillis";
    private static final String PROP_EVICTIONPOLICYCLASSNAME = "evictionPolicyClassName";
    private static final String PROP_TESTWHILEIDLE = "testWhileIdle";
    private static final String PROP_PASSWORD = "password";
    private static final String PROP_URL = "url";
    private static final String PROP_USERNAME = "username";
    private static final String PROP_VALIDATIONQUERY = "validationQuery";
    private static final String PROP_VALIDATIONQUERY_TIMEOUT = "validationQueryTimeout";
    private static final String PROP_JMX_NAME = "jmxName";
    private static final String PROP_CONNECTIONINITSQLS = "connectionInitSqls";
    private static final String PROP_ACCESSTOUNDERLYINGCONNECTIONALLOWED = "accessToUnderlyingConnectionAllowed";
    private static final String PROP_REMOVEABANDONEDONBORROW = "removeAbandonedOnBorrow";
    private static final String PROP_REMOVEABANDONEDONMAINTENANCE = "removeAbandonedOnMaintenance";
    private static final String PROP_REMOVEABANDONEDTIMEOUT = "removeAbandonedTimeout";
    private static final String PROP_LOGABANDONED = "logAbandoned";
    private static final String PROP_ABANDONEDUSAGETRACKING = "abandonedUsageTracking";
    private static final String PROP_POOLPREPAREDSTATEMENTS = "poolPreparedStatements";
    private static final String PROP_MAXOPENPREPAREDSTATEMENTS = "maxOpenPreparedStatements";
    private static final String PROP_CONNECTIONPROPERTIES = "connectionProperties";
    private static final String PROP_MAXCONNLIFETIMEMILLIS = "maxConnLifetimeMillis";
    private static final String PROP_LOGEXPIREDCONNECTIONS = "logExpiredConnections";
    private static final String PROP_ROLLBACK_ON_RETURN = "rollbackOnReturn";
    private static final String PROP_ENABLE_AUTOCOMMIT_ON_RETURN = "enableAutoCommitOnReturn";
    private static final String PROP_DEFAULT_QUERYTIMEOUT = "defaultQueryTimeout";
    private static final String PROP_FASTFAIL_VALIDATION = "fastFailValidation";
    private static final String PROP_DISCONNECTION_SQL_CODES = "disconnectionSqlCodes";
    private static final String NUPROP_MAXACTIVE = "maxActive";
    private static final String NUPROP_REMOVEABANDONED = "removeAbandoned";
    private static final String NUPROP_MAXWAIT = "maxWait";
    private static final String SILENTPROP_FACTORY = "factory";
    private static final String SILENTPROP_SCOPE = "scope";
    private static final String SILENTPROP_SINGLETON = "singleton";
    private static final String SILENTPROP_AUTH = "auth";
    private static final String NONE = "NONE";
    private static final String READ_COMMITTED = "READ_COMMITTED";
    private static final String READ_UNCOMMITTED = "READ_UNCOMMITTED";
    private static final String REPEATABLE_READ = "REPEATABLE_READ";
    private static final String SERIALIZABLE = "SERIALIZABLE";
    private static final String[] ALL_PROPERTIES = new String[]{"defaultAutoCommit", "defaultReadOnly", "defaultTransactionIsolation", "defaultCatalog", "defaultSchema", "cacheState", "driverClassName", "lifo", "maxTotal", "maxIdle", "minIdle", "initialSize", "maxWaitMillis", "testOnCreate", "testOnBorrow", "testOnReturn", "timeBetweenEvictionRunsMillis", "numTestsPerEvictionRun", "minEvictableIdleTimeMillis", "softMinEvictableIdleTimeMillis", "evictionPolicyClassName", "testWhileIdle", "password", "url", "username", "validationQuery", "validationQueryTimeout", "connectionInitSqls", "accessToUnderlyingConnectionAllowed", "removeAbandonedOnBorrow", "removeAbandonedOnMaintenance", "removeAbandonedTimeout", "logAbandoned", "abandonedUsageTracking", "poolPreparedStatements", "maxOpenPreparedStatements", "connectionProperties", "maxConnLifetimeMillis", "logExpiredConnections", "rollbackOnReturn", "enableAutoCommitOnReturn", "defaultQueryTimeout", "fastFailValidation", "disconnectionSqlCodes", "jmxName"};
    private static final Map<String, String> NUPROP_WARNTEXT = new LinkedHashMap();
    private static final List<String> SILENT_PROPERTIES;

    private static final String REF_CLASS_NAME = "javax.sql.DataSource";
    public ProxyBasicDataSourceFactory() {
    }

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        if (obj != null && obj instanceof Reference) {
            Reference ref = (Reference) obj;
            if (!REF_CLASS_NAME.equals(ref.getClassName())) {
                return null;
            } else {
                List<String> warnings = new ArrayList();
                List<String> infoMessages = new ArrayList();
                this.validatePropertyNames(ref, name, warnings, infoMessages);
                Iterator var8 = warnings.iterator();

                String infoMessage;
                while (var8.hasNext()) {
                    infoMessage = (String) var8.next();
                    log.warn(infoMessage);
                }

                var8 = infoMessages.iterator();

                while (var8.hasNext()) {
                    infoMessage = (String) var8.next();
                    log.info(infoMessage);
                }

                Properties properties = new Properties();
                String[] var16 = ALL_PROPERTIES;
                int var10 = var16.length;

                for (int var11 = 0; var11 < var10; ++var11) {
                    String propertyName = var16[var11];
                    RefAddr ra = ref.get(propertyName);
                    if (ra != null) {
                        String propertyValue = ra.getContent().toString();
                        properties.setProperty(propertyName, propertyValue);
                    }
                }

                return createDataSource(properties);
            }
        } else {
            return null;
        }
    }

    private void validatePropertyNames(Reference ref, Name name, List<String> warnings, List<String> infoMessages) {
        List<String> allPropsAsList = Arrays.asList(ALL_PROPERTIES);
        String nameString = name != null ? "Name = " + name.toString() + " " : "";
        if (NUPROP_WARNTEXT != null && !NUPROP_WARNTEXT.keySet().isEmpty()) {
            Iterator var7 = NUPROP_WARNTEXT.keySet().iterator();

            while (var7.hasNext()) {
                String propertyName = (String) var7.next();
                RefAddr ra = ref.get(propertyName);
                if (ra != null && !allPropsAsList.contains(ra.getType())) {
                    StringBuilder stringBuilder = new StringBuilder(nameString);
                    String propertyValue = ra.getContent().toString();
                    stringBuilder.append((String) NUPROP_WARNTEXT.get(propertyName)).append(" You have set value of \"").append(propertyValue).append("\" for \"").append(propertyName).append("\" property, which is being ignored.");
                    warnings.add(stringBuilder.toString());
                }
            }
        }

        Enumeration allRefAddrs = ref.getAll();

        while (allRefAddrs.hasMoreElements()) {
            RefAddr ra = (RefAddr) allRefAddrs.nextElement();
            String propertyName = ra.getType();
            if (!allPropsAsList.contains(propertyName) && !NUPROP_WARNTEXT.keySet().contains(propertyName) && !SILENT_PROPERTIES.contains(propertyName)) {
                String propertyValue = ra.getContent().toString();
                StringBuilder stringBuilder = new StringBuilder(nameString);
                stringBuilder.append("Ignoring unknown property: ").append("value of \"").append(propertyValue).append("\" for \"").append(propertyName).append("\" property");
                infoMessages.add(stringBuilder.toString());
            }
        }

    }

    private static void setDefaultTransactionIsolation(BasicDataSource dataSource, String value){
        if (value != null) {
            int level;
            if (NONE.equalsIgnoreCase(value)) {
                level = 0;
            } else if (READ_COMMITTED.equalsIgnoreCase(value)) {
                level = 2;
            } else if (READ_UNCOMMITTED.equalsIgnoreCase(value)) {
                level = 1;
            } else if (REPEATABLE_READ.equalsIgnoreCase(value)) {
                level = 4;
            } else if (SERIALIZABLE.equalsIgnoreCase(value)) {
                level = 8;
            } else {
                try {
                    level = Integer.parseInt(value);
                } catch (NumberFormatException var6) {
                    System.err.println("Could not parse defaultTransactionIsolation: " + value);
                    System.err.println("WARNING: defaultTransactionIsolation not set");
                    System.err.println("using default value of database driver");
                    level = -1;
                }
            }

            dataSource.setDefaultTransactionIsolation(level);
        }
    }


    @SuppressWarnings("AlibabaMethodTooLong")
    public static BasicDataSource createDataSource(Properties properties) throws Exception {
        BasicDataSource dataSource = new ProxyBasicDataSource();
        String value = properties.getProperty(PROP_DEFAULTAUTOCOMMIT);
        if (value != null) {
            dataSource.setDefaultAutoCommit(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_DEFAULTREADONLY);
        if (value != null) {
            dataSource.setDefaultReadOnly(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_DEFAULTTRANSACTIONISOLATION);
       setDefaultTransactionIsolation(dataSource, value);

        value = properties.getProperty(PROP_DEFAULTCATALOG);
        if (value != null) {
            dataSource.setDefaultCatalog(value);
        }

        value = properties.getProperty(PROP_DEFAULTSCHEMA);
        if (value != null) {
            dataSource.setDefaultSchema(value);
        }

        value = properties.getProperty(PROP_CACHESTATE);
        if (value != null) {
            dataSource.setCacheState(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_DRIVERCLASSNAME);
        if (value != null) {
            dataSource.setDriverClassName(value);
        }

        value = properties.getProperty(PROP_LIFO);
        if (value != null) {
            dataSource.setLifo(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_MAXTOTAL);
        if (value != null) {
            dataSource.setMaxTotal(Integer.parseInt(value));
        }

        value = properties.getProperty(PROP_MAXIDLE);
        if (value != null) {
            dataSource.setMaxIdle(Integer.parseInt(value));
        }

        value = properties.getProperty(PROP_MINIDLE);
        if (value != null) {
            dataSource.setMinIdle(Integer.parseInt(value));
        }

        value = properties.getProperty(PROP_INITIALSIZE);
        if (value != null) {
            dataSource.setInitialSize(Integer.parseInt(value));
        }

        value = properties.getProperty(PROP_MAXWAITMILLIS);
        if (value != null) {
            dataSource.setMaxWaitMillis(Long.parseLong(value));
        }

        value = properties.getProperty(PROP_TESTONCREATE);
        if (value != null) {
            dataSource.setTestOnCreate(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_TESTONBORROW);
        if (value != null) {
            dataSource.setTestOnBorrow(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_TESTONRETURN);
        if (value != null) {
            dataSource.setTestOnReturn(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_TIMEBETWEENEVICTIONRUNSMILLIS);
        if (value != null) {
            dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(value));
        }

        value = properties.getProperty(PROP_NUMTESTSPEREVICTIONRUN);
        if (value != null) {
            dataSource.setNumTestsPerEvictionRun(Integer.parseInt(value));
        }

        value = properties.getProperty(PROP_MINEVICTABLEIDLETIMEMILLIS);
        if (value != null) {
            dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(value));
        }

        value = properties.getProperty(PROP_SOFTMINEVICTABLEIDLETIMEMILLIS);
        if (value != null) {
            dataSource.setSoftMinEvictableIdleTimeMillis(Long.parseLong(value));
        }

        value = properties.getProperty(PROP_EVICTIONPOLICYCLASSNAME);
        if (value != null) {
            dataSource.setEvictionPolicyClassName(value);
        }

        value = properties.getProperty(PROP_TESTWHILEIDLE);
        if (value != null) {
            dataSource.setTestWhileIdle(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_PASSWORD);
        if (value != null) {
            dataSource.setPassword(value);
        }

        value = properties.getProperty(PROP_URL);
        if (value != null) {
            dataSource.setUrl(value);
        }

        value = properties.getProperty(PROP_USERNAME);
        if (value != null) {
            dataSource.setUsername(value);
        }

        value = properties.getProperty(PROP_VALIDATIONQUERY);
        if (value != null) {
            dataSource.setValidationQuery(value);
        }

        value = properties.getProperty("validationquerytimeout");
        if (value != null) {
            dataSource.setValidationQueryTimeout(Integer.parseInt(value));
        }

        value = properties.getProperty(PROP_ACCESSTOUNDERLYINGCONNECTIONALLOWED);
        if (value != null) {
            dataSource.setAccessToUnderlyingConnectionAllowed(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_REMOVEABANDONEDONBORROW);
        if (value != null) {
            dataSource.setRemoveAbandonedOnBorrow(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_REMOVEABANDONEDONMAINTENANCE);
        if (value != null) {
            dataSource.setRemoveAbandonedOnMaintenance(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_REMOVEABANDONEDTIMEOUT);
        if (value != null) {
            dataSource.setRemoveAbandonedTimeout(Integer.parseInt(value));
        }

        value = properties.getProperty(PROP_LOGABANDONED);
        if (value != null) {
            dataSource.setLogAbandoned(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_ABANDONEDUSAGETRACKING);
        if (value != null) {
            dataSource.setAbandonedUsageTracking(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_POOLPREPAREDSTATEMENTS);
        if (value != null) {
            dataSource.setPoolPreparedStatements(Boolean.valueOf(value));
        }

        value = properties.getProperty(PROP_MAXOPENPREPAREDSTATEMENTS);
        if (value != null) {
            dataSource.setMaxOpenPreparedStatements(Integer.parseInt(value));
        }

        value = properties.getProperty(PROP_CONNECTIONINITSQLS);
        if (value != null) {
            dataSource.setConnectionInitSqls(parseList(value, ';'));
        }

        value = properties.getProperty(PROP_CONNECTIONPROPERTIES);
        if (value != null) {
            Properties p = getProperties(value);
            Enumeration e = p.propertyNames();

            while (e.hasMoreElements()) {
                String propertyName = (String) e.nextElement();
                dataSource.addConnectionProperty(propertyName, p.getProperty(propertyName));
            }
        }

        value = properties.getProperty(PROP_MAXCONNLIFETIMEMILLIS);
        if (value != null) {
            dataSource.setMaxConnLifetimeMillis(Long.parseLong(value));
        }

        value = properties.getProperty(PROP_LOGEXPIREDCONNECTIONS);
        if (value != null) {
            dataSource.setLogExpiredConnections(Boolean.valueOf(value));
        }

        value = properties.getProperty("jmxName");
        if (value != null) {
            dataSource.setJmxName(value);
        }

        value = properties.getProperty("enableAutoCommitOnReturn");
        if (value != null) {
            dataSource.setEnableAutoCommitOnReturn(Boolean.valueOf(value));
        }

        value = properties.getProperty("rollbackOnReturn");
        if (value != null) {
            dataSource.setRollbackOnReturn(Boolean.valueOf(value));
        }

        value = properties.getProperty("defaultQueryTimeout");
        if (value != null) {
            dataSource.setDefaultQueryTimeout(Integer.valueOf(value));
        }

        value = properties.getProperty("fastFailValidation");
        if (value != null) {
            dataSource.setFastFailValidation(Boolean.valueOf(value));
        }

        value = properties.getProperty("disconnectionSqlCodes");
        if (value != null) {
            dataSource.setDisconnectionSqlCodes(parseList(value, ','));
        }

        if (dataSource.getInitialSize() > 0) {
            dataSource.getLogWriter();
        }

        return dataSource;
    }

    private static Properties getProperties(String propText) throws Exception {
        Properties p = new Properties();
        if (propText != null) {
            p.load(new ByteArrayInputStream(propText.replace(';', '\n').getBytes(StandardCharsets.ISO_8859_1)));
        }

        return p;
    }

    private static Collection<String> parseList(String value, char delimiter) {
        StringTokenizer tokenizer = new StringTokenizer(value, Character.toString(delimiter));
        ArrayList tokens = new ArrayList(tokenizer.countTokens());

        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }

        return tokens;
    }

    static {
        NUPROP_WARNTEXT.put("maxActive", "Property maxActive is not used in DBCP2, use maxTotal instead. maxTotal default value is 8.");
        NUPROP_WARNTEXT.put("removeAbandoned", "Property removeAbandoned is not used in DBCP2, use one or both of removeAbandonedOnBorrow or removeAbandonedOnMaintenance instead. Both have default value set to false.");
        NUPROP_WARNTEXT.put("maxWait", "Property maxWait is not used in DBCP2 , use maxWaitMillis instead. maxWaitMillis default value is -1.");
        SILENT_PROPERTIES = new ArrayList();
        SILENT_PROPERTIES.add("factory");
        SILENT_PROPERTIES.add("scope");
        SILENT_PROPERTIES.add("singleton");
        SILENT_PROPERTIES.add("auth");
    }
}
