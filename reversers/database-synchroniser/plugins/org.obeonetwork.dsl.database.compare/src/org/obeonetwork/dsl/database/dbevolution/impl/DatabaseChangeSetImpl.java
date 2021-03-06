/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.obeonetwork.dsl.database.dbevolution.impl;

import org.eclipse.emf.compare.internal.spec.ComparisonSpec;
import org.eclipse.emf.ecore.EClass;
import org.obeonetwork.dsl.database.dbevolution.DatabaseChangeSet;
import org.obeonetwork.dsl.database.dbevolution.DbevolutionPackage;

/**
 * We have to inherit ComparisonSpec to get all the implementations
 *
 * @generated NOT
 */
@SuppressWarnings("restriction")
public class DatabaseChangeSetImpl extends ComparisonSpec implements DatabaseChangeSet {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected DatabaseChangeSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DbevolutionPackage.Literals.DATABASE_CHANGE_SET;
	}

} // DatabaseChangeSetImpl
