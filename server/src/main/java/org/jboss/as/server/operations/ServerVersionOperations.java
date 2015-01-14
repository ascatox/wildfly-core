/*
* JBoss, Home of Professional Open Source.
* Copyright 2011, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jboss.as.server.operations;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.server.controller.resources.ServerRootResourceDefinition;
import org.jboss.dmr.ModelNode;

/**
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
public class ServerVersionOperations {

    public static class DefaultEmptyListAttributeHandler implements OperationStepHandler {
        public static final OperationStepHandler INSTANCE = new DefaultEmptyListAttributeHandler();
        @Override
        public void execute(OperationContext context, ModelNode operation) throws OperationFailedException {
            String attr = operation.get(ModelDescriptionConstants.NAME).asString();
            if (attr.equals(ModelDescriptionConstants.NAMESPACES)) {
                getAttributeValueOrDefault(ServerRootResourceDefinition.NAMESPACES, context);
            } else if (attr.equals(ModelDescriptionConstants.SCHEMA_LOCATIONS)) {
                getAttributeValueOrDefault(ServerRootResourceDefinition.SCHEMA_LOCATIONS, context);
            }
        }

        private void getAttributeValueOrDefault(AttributeDefinition def, OperationContext context) throws OperationFailedException {
            //TODO fails in the validator
            //final ModelNode result = def.resolveModelAttribute(context, context.readResource(PathAddress.EMPTY_ADDRESS, false).getModel());
            final ModelNode result = context.readResource(PathAddress.EMPTY_ADDRESS, false).getModel().get(def.getName());
            context.getResult().set(result.isDefined() ? result : new ModelNode().setEmptyList());
        }
    }
}
