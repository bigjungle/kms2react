import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import kmsInfo, {
  KmsInfoSdmSuffixState
} from 'app/entities/kms-info-sdm-suffix/kms-info-sdm-suffix.reducer';
// prettier-ignore
import verifyRec, {
  VerifyRecSdmSuffixState
} from 'app/entities/verify-rec-sdm-suffix/verify-rec-sdm-suffix.reducer';
// prettier-ignore
import paraType, {
  ParaTypeSdmSuffixState
} from 'app/entities/para-type-sdm-suffix/para-type-sdm-suffix.reducer';
// prettier-ignore
import paraClass, {
  ParaClassSdmSuffixState
} from 'app/entities/para-class-sdm-suffix/para-class-sdm-suffix.reducer';
// prettier-ignore
import paraCat, {
  ParaCatSdmSuffixState
} from 'app/entities/para-cat-sdm-suffix/para-cat-sdm-suffix.reducer';
// prettier-ignore
import paraState, {
  ParaStateSdmSuffixState
} from 'app/entities/para-state-sdm-suffix/para-state-sdm-suffix.reducer';
// prettier-ignore
import paraSource, {
  ParaSourceSdmSuffixState
} from 'app/entities/para-source-sdm-suffix/para-source-sdm-suffix.reducer';
// prettier-ignore
import paraProp, {
  ParaPropSdmSuffixState
} from 'app/entities/para-prop-sdm-suffix/para-prop-sdm-suffix.reducer';
// prettier-ignore
import paraAttr, {
  ParaAttrSdmSuffixState
} from 'app/entities/para-attr-sdm-suffix/para-attr-sdm-suffix.reducer';
// prettier-ignore
import paraOther, {
  ParaOtherSdmSuffixState
} from 'app/entities/para-other-sdm-suffix/para-other-sdm-suffix.reducer';
// prettier-ignore
import paraUser, {
  ParaUserSdmSuffixState
} from 'app/entities/para-user-sdm-suffix/para-user-sdm-suffix.reducer';
// prettier-ignore
import paraDep, {
  ParaDepSdmSuffixState
} from 'app/entities/para-dep-sdm-suffix/para-dep-sdm-suffix.reducer';
// prettier-ignore
import paraRole, {
  ParaRoleSdmSuffixState
} from 'app/entities/para-role-sdm-suffix/para-role-sdm-suffix.reducer';
// prettier-ignore
import paraNode, {
  ParaNodeSdmSuffixState
} from 'app/entities/para-node-sdm-suffix/para-node-sdm-suffix.reducer';
// prettier-ignore
import queryCommon50, {
  QueryCommon50SdmSuffixState
} from 'app/entities/query-common-50-sdm-suffix/query-common-50-sdm-suffix.reducer';
// prettier-ignore
import queryCommon10, {
  QueryCommon10SdmSuffixState
} from 'app/entities/query-common-10-sdm-suffix/query-common-10-sdm-suffix.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly kmsInfo: KmsInfoSdmSuffixState;
  readonly verifyRec: VerifyRecSdmSuffixState;
  readonly paraType: ParaTypeSdmSuffixState;
  readonly paraClass: ParaClassSdmSuffixState;
  readonly paraCat: ParaCatSdmSuffixState;
  readonly paraState: ParaStateSdmSuffixState;
  readonly paraSource: ParaSourceSdmSuffixState;
  readonly paraProp: ParaPropSdmSuffixState;
  readonly paraAttr: ParaAttrSdmSuffixState;
  readonly paraOther: ParaOtherSdmSuffixState;
  readonly paraUser: ParaUserSdmSuffixState;
  readonly paraDep: ParaDepSdmSuffixState;
  readonly paraRole: ParaRoleSdmSuffixState;
  readonly paraNode: ParaNodeSdmSuffixState;
  readonly queryCommon50: QueryCommon50SdmSuffixState;
  readonly queryCommon10: QueryCommon10SdmSuffixState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  kmsInfo,
  verifyRec,
  paraType,
  paraClass,
  paraCat,
  paraState,
  paraSource,
  paraProp,
  paraAttr,
  paraOther,
  paraUser,
  paraDep,
  paraRole,
  paraNode,
  queryCommon50,
  queryCommon10,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
