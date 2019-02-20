import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import KmsInfoSdmSuffix from './kms-info-sdm-suffix';
import VerifyRecSdmSuffix from './verify-rec-sdm-suffix';
import ParaTypeSdmSuffix from './para-type-sdm-suffix';
import ParaClassSdmSuffix from './para-class-sdm-suffix';
import ParaCatSdmSuffix from './para-cat-sdm-suffix';
import ParaStateSdmSuffix from './para-state-sdm-suffix';
import ParaSourceSdmSuffix from './para-source-sdm-suffix';
import ParaPropSdmSuffix from './para-prop-sdm-suffix';
import ParaAttrSdmSuffix from './para-attr-sdm-suffix';
import ParaOtherSdmSuffix from './para-other-sdm-suffix';
import ParaUserSdmSuffix from './para-user-sdm-suffix';
import ParaDepSdmSuffix from './para-dep-sdm-suffix';
import ParaRoleSdmSuffix from './para-role-sdm-suffix';
import ParaNodeSdmSuffix from './para-node-sdm-suffix';
import QueryCommon50SdmSuffix from './query-common-50-sdm-suffix';
import QueryCommon10SdmSuffix from './query-common-10-sdm-suffix';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/kms-info-sdm-suffix`} component={KmsInfoSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/verify-rec-sdm-suffix`} component={VerifyRecSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/para-type-sdm-suffix`} component={ParaTypeSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/para-class-sdm-suffix`} component={ParaClassSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/para-cat-sdm-suffix`} component={ParaCatSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/para-state-sdm-suffix`} component={ParaStateSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/para-source-sdm-suffix`} component={ParaSourceSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/para-prop-sdm-suffix`} component={ParaPropSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/para-attr-sdm-suffix`} component={ParaAttrSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/para-other-sdm-suffix`} component={ParaOtherSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/para-user-sdm-suffix`} component={ParaUserSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/para-dep-sdm-suffix`} component={ParaDepSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/para-role-sdm-suffix`} component={ParaRoleSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/para-node-sdm-suffix`} component={ParaNodeSdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/query-common-50-sdm-suffix`} component={QueryCommon50SdmSuffix} />
      <ErrorBoundaryRoute path={`${match.url}/query-common-10-sdm-suffix`} component={QueryCommon10SdmSuffix} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
