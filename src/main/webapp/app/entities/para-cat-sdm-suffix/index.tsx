import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParaCatSdmSuffix from './para-cat-sdm-suffix';
import ParaCatSdmSuffixDetail from './para-cat-sdm-suffix-detail';
import ParaCatSdmSuffixUpdate from './para-cat-sdm-suffix-update';
import ParaCatSdmSuffixDeleteDialog from './para-cat-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParaCatSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParaCatSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParaCatSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParaCatSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParaCatSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
