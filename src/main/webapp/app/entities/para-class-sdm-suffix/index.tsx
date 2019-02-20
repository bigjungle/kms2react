import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParaClassSdmSuffix from './para-class-sdm-suffix';
import ParaClassSdmSuffixDetail from './para-class-sdm-suffix-detail';
import ParaClassSdmSuffixUpdate from './para-class-sdm-suffix-update';
import ParaClassSdmSuffixDeleteDialog from './para-class-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParaClassSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParaClassSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParaClassSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParaClassSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParaClassSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
