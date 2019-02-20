import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './para-dep-sdm-suffix.reducer';
import { IParaDepSdmSuffix } from 'app/shared/model/para-dep-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParaDepSdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ParaDepSdmSuffixDetail extends React.Component<IParaDepSdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { paraDepEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.paraDep.detail.title">ParaDep</Translate> [<b>{paraDepEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="kmsApp.paraDep.name">Name</Translate>
              </span>
              <UncontrolledTooltip target="name">
                <Translate contentKey="kmsApp.paraDep.help.name" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraDepEntity.name}</dd>
            <dt>
              <span id="serialNumber">
                <Translate contentKey="kmsApp.paraDep.serialNumber">Serial Number</Translate>
              </span>
              <UncontrolledTooltip target="serialNumber">
                <Translate contentKey="kmsApp.paraDep.help.serialNumber" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraDepEntity.serialNumber}</dd>
            <dt>
              <span id="descString">
                <Translate contentKey="kmsApp.paraDep.descString">Desc String</Translate>
              </span>
              <UncontrolledTooltip target="descString">
                <Translate contentKey="kmsApp.paraDep.help.descString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraDepEntity.descString}</dd>
            <dt>
              <span id="tel">
                <Translate contentKey="kmsApp.paraDep.tel">Tel</Translate>
              </span>
              <UncontrolledTooltip target="tel">
                <Translate contentKey="kmsApp.paraDep.help.tel" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraDepEntity.tel}</dd>
            <dt>
              <span id="address">
                <Translate contentKey="kmsApp.paraDep.address">Address</Translate>
              </span>
              <UncontrolledTooltip target="address">
                <Translate contentKey="kmsApp.paraDep.help.address" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraDepEntity.address}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="kmsApp.paraDep.remarks">Remarks</Translate>
              </span>
              <UncontrolledTooltip target="remarks">
                <Translate contentKey="kmsApp.paraDep.help.remarks" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraDepEntity.remarks}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraDep.createdUser">Created User</Translate>
            </dt>
            <dd>{paraDepEntity.createdUserName ? paraDepEntity.createdUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraDep.modifiedUser">Modified User</Translate>
            </dt>
            <dd>{paraDepEntity.modifiedUserName ? paraDepEntity.modifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraDep.verifiedUser">Verified User</Translate>
            </dt>
            <dd>{paraDepEntity.verifiedUserName ? paraDepEntity.verifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraDep.parent">Parent</Translate>
            </dt>
            <dd>{paraDepEntity.parentName ? paraDepEntity.parentName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/para-dep-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/para-dep-sdm-suffix/${paraDepEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ paraDep }: IRootState) => ({
  paraDepEntity: paraDep.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ParaDepSdmSuffixDetail);
