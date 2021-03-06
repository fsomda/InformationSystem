/*******************************************************************************
 * Copyright (c) 2016-2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package fr.gouv.mindef.safran.project.lifecycle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.modelingproject.ModelingProject;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ui.tools.internal.views.common.modelingproject.OpenRepresentationsFileJob;
import org.eclipse.sirius.viewpoint.DRepresentation;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

@SuppressWarnings("restriction")
public class ImporterUtil {
	
	public static Session getSession(ModelingProject project) {
		Session session = project.getSession();
		if (session == null) {
			final Option<URI> optionalMainSessionFileURI = project.getMainRepresentationsFileURI(new NullProgressMonitor(), false, false);
			if (optionalMainSessionFileURI.some()) {
				URI representationsFileURI = optionalMainSessionFileURI.get();
				OpenRepresentationsFileJob job = new OpenRepresentationsFileJob(representationsFileURI);
				Set<Session> sessions = job.performOpenSession(representationsFileURI, new NullProgressMonitor());
				if (!sessions.isEmpty()) {
					return sessions.iterator().next();
				}
			}
		}
		return session;
	}
	
	public static boolean isOfAnyClass(EObject object, Collection<EClass> eClasses) {
		for (EClass eClass : eClasses) {
			if (eClass.isInstance(object)) {
				return true;
			}
		}
		return false;
	}
	

	public static Collection<DRepresentation> getRelatedRepresentations(Session session, Collection<EObject> semanticElements) {
		Collection<DRepresentation> representations = new ArrayList<DRepresentation>();
		
		for (EObject semanticElement : semanticElements) {
			representations.addAll(DialectManager.INSTANCE.getRepresentations(semanticElement, session));
		}
		return representations;
	}
	
	public static Collection<ReferenceData> getReferencingElements(Collection<EObject> elements, Session session) {
		Collection<ReferenceData> referencesData = new ArrayList<ReferenceData>();
		
		Collection<EObject> allElements = getAllElementsWithChildren(elements);
		
		for (EObject element : allElements) {
			Collection<Setting> inverseReferences = session.getSemanticCrossReferencer().getInverseReferences(element, true);
			for (Setting setting : inverseReferences) {
				referencesData.add(new ReferenceData(element, setting));
			}		
		}
		return referencesData;
	}
	
	
	public static Collection<EObject> getAllElementsWithChildren(Collection<EObject> roots) {
		Collection<EObject> children = new ArrayList<EObject>(roots);
		// Collect all children
		for (EObject root : roots) {
			Collection<EObject> ownedChildren = Lists.newArrayList(root.eAllContents());
			children.addAll(ownedChildren);
		}
		return children;
	}
	
	public static <T extends EObject> List<T> getAllContentsOfType(EObject root, Class<T> type) {
		return Lists.newArrayList(Iterators.filter(root.eAllContents(), type));
	}
	
}
