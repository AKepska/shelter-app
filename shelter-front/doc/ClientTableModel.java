package com.javafee.elibrary.core.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.ClientTableColumn;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Validator;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.hibernate.dao.HibernateDao;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Client;

public class ClientTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	protected List<Client> clients;
	protected HibernateDao<Client, Integer> clientDao;
	private String[] columns;

	public ClientTableModel() {
		super();
		this.prepareHibernateDao();
		this.columns = new String[]{
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.peselNumberCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.documentNumberCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.loginCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.eMailCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.nameCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.surnameCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.addressCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.cityCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.sexCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.birthDateCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredCol"),
				SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.blockedCol")};
	}

	public Client getClient(int index) {
		return clients.get(index);
	}

	public void setClient(int index, Client client) {
		clients.set(index, client);
	}

	public void add(Client client) {
		clients.add(client);
		this.fireTableDataChanged();
	}

	public void remove(Client client) {
		clients.remove(client);
		this.fireTableDataChanged();
	}

	protected void prepareHibernateDao() {
		this.clientDao = new HibernateDao<Client, Integer>(Client.class);
		this.clients = clientDao.findAll();
	}

	private void executeUpdate(String entityName, Object object) {
		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().update(entityName, object);
		HibernateUtil.commitTransaction();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return clients.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Client client = clients.get(row);
		switch (ClientTableColumn.getByNumber(col)) {
			case COL_PESEL_NUMBER:
				return client.getPeselNumber();
			case COL_DOCUMENT_NUMBER:
				return client.getDocumentNumber();
			case COL_LOGIN:
				return client.getUserAccount().getLogin();
			case COL_E_MAIL:
				return client.getEMail();
			case COL_NAME:
				return client.getName();
			case COL_SURNAME:
				return client.getSurname();
			case COL_ADDRESS:
				return client.getAddress();
			case COL_CITY:
				return client.getCity();
			case COL_SEX:
				if (client.getSex() != null) {
					if (Constants.DATA_BASE_MALE_SIGN.toString().equals(client.getSex().toString()))
						return SystemProperties.getInstance().getResourceBundle()
								.getString("clientTableModel.registeredMaleVal");
					else if (Constants.DATA_BASE_FEMALE_SIGN.toString().equals(client.getSex().toString()))
						return SystemProperties.getInstance().getResourceBundle()
								.getString("clientTableModel.registeredFemaleVal");
				} else
					return null;
			case COL_BIRTH_DATE:
				return client.getBirthDate() != null ? Constants.APPLICATION_DATE_FORMAT.format(client.getBirthDate())
						: null;
			case COL_REGISTERED:
				return client.getUserAccount().getRegistered()
						? SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredTrueVal")
						: SystemProperties.getInstance().getResourceBundle()
						.getString("clientTableModel.registeredFalseVal");
			case COL_BLOCKED:
				return Optional.ofNullable(client.getUserAccount()).isPresent() && client.getUserAccount().getBlocked()
						? SystemProperties.getInstance().getResourceBundle().getString("clientTableModel.registeredTrueVal")
						: SystemProperties.getInstance().getResourceBundle()
						.getString("clientTableModel.registeredFalseVal");
			default:
				return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Client client = clients.get(row);
		Client clientShallowClone = (Client) client.clone();

		switch (ClientTableColumn.getByNumber(col)) {
			case COL_PESEL_NUMBER:
				clientShallowClone.setPeselNumber(value.toString());
				break;
			case COL_DOCUMENT_NUMBER:
				clientShallowClone.setDocumentNumber(value.toString());
				break;
			case COL_LOGIN:
				clientShallowClone.getUserAccount().setLogin(value.toString());
			case COL_E_MAIL:
				clientShallowClone.setEMail(value.toString());
				break;
			case COL_NAME:
				clientShallowClone.setName(value.toString());
				break;
			case COL_SURNAME:
				clientShallowClone.setSurname(value.toString());
				break;
			case COL_ADDRESS:
				clientShallowClone.setAddress(value.toString());
				break;
			case COL_CITY:
				clientShallowClone.setCity(Optional.ofNullable(value).isPresent() ? value.toString() : null);
				break;
			case COL_SEX:
				clientShallowClone.setSex((Character) value);
				break;
			case COL_BIRTH_DATE:
				clientShallowClone.setBirthDate((Date) value);
				break;
			case COL_REGISTERED:
				clientShallowClone.getUserAccount().setRegistered((Boolean) value);
				break;
			case COL_BLOCKED:
				if (Optional.ofNullable(clientShallowClone.getUserAccount()).isPresent())
					clientShallowClone.getUserAccount().setBlocked((Boolean) value);
				break;
		}

		if (Validator.validateClientUpdate(clientShallowClone)) {
			executeUpdate(Client.class.getName(), client);
			clients.set(row, clientShallowClone);
		} else {
			LogGuiException.logWarning(
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientTableModel.constraintViolationErrorTitle"),
					SystemProperties.getInstance().getResourceBundle()
							.getString("clientTableModel.constraintViolationError"));
		}

		this.fireTableRowsUpdated(row, row);
	}

	@Override
	public String getColumnName(int col) {
		return columns[col];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void reloadData() {
		prepareHibernateDao();
		fireTableDataChanged();
	}

	@Override
	public void fireTableChanged(TableModelEvent e) {
		super.fireTableChanged(e);
	}
}