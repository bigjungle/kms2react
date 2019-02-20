import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParaNodeSdmSuffix from './para-node-sdm-suffix';
import ParaNodeSdmSuffixDetail from './para-node-sdm-suffix-detail';
import ParaNodeSdmSuffixUpdate from './para-node-sdm-suffix-update';
import ParaNodeSdmSuffixDeleteDialog from './para-node-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParaNodeSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParaNodeSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParaNodeSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParaNodeSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParaNodeSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
