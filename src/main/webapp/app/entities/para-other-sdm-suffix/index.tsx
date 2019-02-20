import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParaOtherSdmSuffix from './para-other-sdm-suffix';
import ParaOtherSdmSuffixDetail from './para-other-sdm-suffix-detail';
import ParaOtherSdmSuffixUpdate from './para-other-sdm-suffix-update';
import ParaOtherSdmSuffixDeleteDialog from './para-other-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParaOtherSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParaOtherSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParaOtherSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParaOtherSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParaOtherSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
