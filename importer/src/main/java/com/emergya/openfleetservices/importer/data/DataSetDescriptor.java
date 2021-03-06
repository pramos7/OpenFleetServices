/*
 * Copyright (C) 2012, Emergya (http://www.emergya.com)
 *
 * @author <a href="mailto:marias@emergya.com">María Arias de Reyna</a>
 *
 * This file is part of GoFleetLS
 *
 * This software is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 */
package com.emergya.openfleetservices.importer.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Describe a dataset
 * 
 * @author marias
 * 
 */
public class DataSetDescriptor {

	public File file = null;
	private String tablename = "default";
	private String namePrimaryKey = "pk";
	private String geoColumnName = "the_geom";
	private List<Column> fields = new ArrayList<Column>();
	
	public File getFile(){
		return file;
	}
	
	public void setFile(File file){
		this.file = file;
	}
	public String getNamePK(){
		return this.namePrimaryKey;
	}
	
	public boolean setNamePK(String namePK){
		boolean res = false;
		List<Column> fields = this.getFields();
		for(Column c: fields){
			if(!c.getName().equals(namePK)){
				res = true;
				this.namePrimaryKey = namePK;
			}
		}
		return res;
	}

	/**
	 * @return the geoColumnName
	 */
	public String getGeoColumnName() {
		return geoColumnName;
	}

	/**
	 * @param geoColumnName the geoColumnName to set
	 */
	public boolean setGeoColumnName(String geoColumnName) {
		boolean res = false;
		List<Column> fields = this.getFields();
		for(Column c: fields){
			if(!c.getName().equals(geoColumnName)){
				res = true;
				this.geoColumnName = geoColumnName;
			}else{
				int i = 0;
				while(c.getName().equals(geoColumnName)){
					res = this.setGeoColumnName(c.getName() + String.valueOf(i));
					i++;
				}
			}
		}
		return res;
	}

	public DataSetDescriptor(File file) {
		this.file = file;
	}

	/**
	 * @return the tablename
	 */
	public String getTablename() {
		return tablename;
	}

	/**
	 * @param tablename
	 *            the tablename to set
	 */
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}


	public void addField(Column c) {
		// Check if the column pk is already exist
		if(c.getName().equals(this.getNamePK())){
			int i=0;
			while(!this.setNamePK(this.getNamePK() + String.valueOf(i))){
				i++;
			}
		}
		// Check if any name is repeated
		List<Column> fields = this.getFields();
		for(Column col: fields){
			int i = 0;
			while(col.getName().equals(c.getName())){
				c.setName(col.getName() + String.valueOf(i));
				addField(c);
				i++;
				return;
			}
		}
		fields.add(c);
	}

	public List<Column> getFields() {
		return fields;
	}

}