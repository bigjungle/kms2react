import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParaDepSdmSuffix from './para-dep-sdm-suffix';
import ParaDepSdmSuffixDetail from './para-dep-sdm-suffix-detail';
import ParaDepSdmSuffixUpdate from './para-dep-sdm-suffix-update';
import ParaDepSdmSuffixDeleteDialog from './para-dep-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParaDepSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParaDepSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParaDepSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParaDepSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParaDepSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
