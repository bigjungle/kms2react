import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParaSourceSdmSuffix from './para-source-sdm-suffix';
import ParaSourceSdmSuffixDetail from './para-source-sdm-suffix-detail';
import ParaSourceSdmSuffixUpdate from './para-source-sdm-suffix-update';
import ParaSourceSdmSuffixDeleteDialog from './para-source-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParaSourceSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParaSourceSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParaSourceSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParaSourceSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParaSourceSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
