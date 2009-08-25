package gov.nih.nci.ncicb.cadsr.bulkloader.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadProperties;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.UnloadProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;

public class BulkLoaderUnclassifier {

	private static Log log = LogFactory.getLog(BulkLoaderUnclassifier.class.getName());
	
	private DataSource dataSource;
	
	private String checkCsCsiQry;
	private String csLatestVersionQry;
	private String csiLatestVersionQry;
	private String deleteDefsQry;
	private String deleteAltNamesQry;
	private String deleteAttrsQry;
	private String deleteAdminCompCSCSIQry;
	
	private String csName;
	private Double csVersion;
	private String csiName;
	private Double csiVersion;
	
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getCheckCsCsiQry() {
		return checkCsCsiQry;
	}
	public void setCheckCsCsiQry(String checkCsCsiQry) {
		this.checkCsCsiQry = checkCsCsiQry;
	}
	public String getCsLatestVersionQry() {
		return csLatestVersionQry;
	}
	public void setCsLatestVersionQry(String csLatestVersionQry) {
		this.csLatestVersionQry = csLatestVersionQry;
	}
	public String getCsiLatestVersionQry() {
		return csiLatestVersionQry;
	}
	public void setCsiLatestVersionQry(String csiLatestVersionQry) {
		this.csiLatestVersionQry = csiLatestVersionQry;
	}
	public String getDeleteDefsQry() {
		return deleteDefsQry;
	}
	public void setDeleteDefsQry(String deleteDefsQry) {
		this.deleteDefsQry = deleteDefsQry;
	}
	public String getDeleteAltNamesQry() {
		return deleteAltNamesQry;
	}
	public void setDeleteAltNamesQry(String deleteAltNamesQry) {
		this.deleteAltNamesQry = deleteAltNamesQry;
	}
	public String getDeleteAttrsQry() {
		return deleteAttrsQry;
	}
	public void setDeleteAttrsQry(String deleteAttrsQry) {
		this.deleteAttrsQry = deleteAttrsQry;
	}
	public String getDeleteAdminCompCSCSIQry() {
		return deleteAdminCompCSCSIQry;
	}
	public void setDeleteAdminCompCSCSIQry(String deleteAdminCompCSCSIQry) {
		this.deleteAdminCompCSCSIQry = deleteAdminCompCSCSIQry;
	}
	
	public synchronized void unclassify(UnloadProperties unloadProperties) {
		init(unloadProperties);
		
		if (checkCSCSI()) {
			log.info("Unclassifying Definitions...");
			deleteDefinitions();
			
			log.info("Unclassifying Alternate Names...");
			deleteAlternateNames();
			
			log.info("Unclassifying Attributes...");
			deleteAttributes();
			
			log.info("Unclassifying Admin Components...");
			deleteAdminComponentCSCSI();
		}
	}
	
	private void init(UnloadProperties unloadProperties) {
		this.csName = unloadProperties.getClassificationSchemeName();
		this.csiName = unloadProperties.getClassificationSchemeItemName();
		this.csVersion = getCSVersion(unloadProperties);
		this.csiVersion = getCSIVersion(unloadProperties);
	}
	
	private Double getCSVersion(UnloadProperties unloadProperties) {
		Double csVersion = new Double(unloadProperties.getCsVersion());
		
		if (csVersion.equals(new Double(0.0))) {
			csVersion = getLatestCSVersion(unloadProperties);
		}
		
		return csVersion;
	}
	
	private Double getLatestCSVersion(UnloadProperties unloadProperties) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String csName = unloadProperties.getClassificationSchemeName();
		
		Double latestVersion = (Double)jdbcTemplate.query(csLatestVersionQry, new Object[]{csName}, new ResultSetExtractor() {

			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return new Double(rs.getDouble(1));
				}
				return new Double(0.0);
			}
		});
		
		return latestVersion;
	}
	
	private Double getCSIVersion(UnloadProperties unloadProperties) {
		Double csiVersion = new Double(unloadProperties.getCsiVersion());
		
		if (csiVersion.equals(new Double(0.0))) {
			csiVersion = getLatestCSIVersion(unloadProperties);
		}
		
		return csiVersion;
	}
	
	private Double getLatestCSIVersion(UnloadProperties unloadProperties) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String csName = unloadProperties.getClassificationSchemeName();
		String csiName = unloadProperties.getClassificationSchemeItemName();
		
		Double latestVersion = (Double)jdbcTemplate.query(csiLatestVersionQry, new Object[]{csiName, csName}, new ResultSetExtractor() {

			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return new Double(rs.getDouble(1));
				}
				return new Double(0.0);
			}
		});
		
		return latestVersion;
	}
	
	private boolean checkCSCSI() {
				
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		Boolean checkCsCsi = (Boolean)jdbcTemplate.query(checkCsCsiQry, new Object[]{csName, csVersion, csiName, csiVersion}, new ResultSetExtractor() {

			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return new Boolean(true);
				}
				return new Boolean(false);
			}
		});
		return checkCsCsi;
	}
	
	private void deleteDefinitions() {
		runDeleteQry(this.deleteDefsQry, new Object[]{csName, csVersion, csiName, csiVersion});
	}
	
	private void deleteAlternateNames() {
		runDeleteQry(this.deleteAltNamesQry, new Object[]{csName, csVersion, csiName, csiVersion});
	}
	
	private void deleteAttributes() {
		runDeleteQry(this.deleteAttrsQry, new Object[]{csName, csVersion, csiName, csiVersion});
	}
	
	private void deleteAdminComponentCSCSI() {
		runDeleteQry(this.deleteAdminCompCSCSIQry, new Object[]{csName, csVersion, csiName, csiVersion});
	}
	
	private void runDeleteQry(String qry, final Object[] args) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute(qry, new PreparedStatementCallback() {

			@Override
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				
				for (int i=0;i<args.length;i++) {
					Object o = args[i];
					if (o instanceof String) {
						ps.setString(i, (String) o);
					}
					else if (o instanceof Integer) {
						ps.setInt(i, (Integer) o);
					}
					else if (o instanceof Double) {
						ps.setDouble(i, (Double) o);
					}
				}
				
				return null;
			}
			
		});
	}
	
}
