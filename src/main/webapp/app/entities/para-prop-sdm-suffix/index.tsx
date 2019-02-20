import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParaPropSdmSuffix from './para-prop-sdm-suffix';
import ParaPropSdmSuffixDetail from './para-prop-sdm-suffix-detail';
import ParaPropSdmSuffixUpdate from './para-prop-sdm-suffix-update';
import ParaPropSdmSuffixDeleteDialog from './para-prop-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParaPropSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParaPropSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParaPropSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParaPropSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParaPropSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
