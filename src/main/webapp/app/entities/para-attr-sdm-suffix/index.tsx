import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParaAttrSdmSuffix from './para-attr-sdm-suffix';
import ParaAttrSdmSuffixDetail from './para-attr-sdm-suffix-detail';
import ParaAttrSdmSuffixUpdate from './para-attr-sdm-suffix-update';
import ParaAttrSdmSuffixDeleteDialog from './para-attr-sdm-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParaAttrSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParaAttrSdmSuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParaAttrSdmSuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParaAttrSdmSuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParaAttrSdmSuffixDeleteDialog} />
  </>
);

export default Routes;
