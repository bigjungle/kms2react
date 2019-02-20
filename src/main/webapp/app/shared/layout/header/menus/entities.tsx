import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <DropdownItem tag={Link} to="/entity/kms-info-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.kmsInfoSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/verify-rec-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.verifyRecSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/para-type-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.paraTypeSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/para-class-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.paraClassSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/para-cat-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.paraCatSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/para-state-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.paraStateSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/para-source-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.paraSourceSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/para-prop-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.paraPropSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/para-attr-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.paraAttrSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/para-other-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.paraOtherSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/para-user-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.paraUserSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/para-dep-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.paraDepSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/para-role-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.paraRoleSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/para-node-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.paraNodeSdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/query-common-50-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.queryCommon50SdmSuffix" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/query-common-10-sdm-suffix">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.queryCommon10SdmSuffix" />
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
