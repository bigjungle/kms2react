import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './para-prop-sdm-suffix.reducer';
import { IParaPropSdmSuffix } from 'app/shared/model/para-prop-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParaPropSdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ParaPropSdmSuffixDetail extends React.Component<IParaPropSdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { paraPropEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.paraProp.detail.title">ParaProp</Translate> [<b>{paraPropEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="kmsApp.paraProp.name">Name</Translate>
              </span>
              <UncontrolledTooltip target="name">
                <Translate contentKey="kmsApp.paraProp.help.name" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraPropEntity.name}</dd>
            <dt>
              <span id="serialNumber">
                <Translate contentKey="kmsApp.paraProp.serialNumber">Serial Number</Translate>
              </span>
              <UncontrolledTooltip target="serialNumber">
                <Translate contentKey="kmsApp.paraProp.help.serialNumber" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraPropEntity.serialNumber}</dd>
            <dt>
              <span id="sortString">
                <Translate contentKey="kmsApp.paraProp.sortString">Sort String</Translate>
              </span>
              <UncontrolledTooltip target="sortString">
                <Translate contentKey="kmsApp.paraProp.help.sortString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraPropEntity.sortString}</dd>
            <dt>
              <span id="descString">
                <Translate contentKey="kmsApp.paraProp.descString">Desc String</Translate>
              </span>
              <UncontrolledTooltip target="descString">
                <Translate contentKey="kmsApp.paraProp.help.descString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraPropEntity.descString}</dd>
            <dt>
              <span id="imageBlob">
                <Translate contentKey="kmsApp.paraProp.imageBlob">Image Blob</Translate>
              </span>
              <UncontrolledTooltip target="imageBlob">
                <Translate contentKey="kmsApp.paraProp.help.imageBlob" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              {paraPropEntity.imageBlob ? (
                <div>
                  <a onClick={openFile(paraPropEntity.imageBlobContentType, paraPropEntity.imageBlob)}>
                    <img
                      src={`data:${paraPropEntity.imageBlobContentType};base64,${paraPropEntity.imageBlob}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {paraPropEntity.imageBlobContentType}, {byteSize(paraPropEntity.imageBlob)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="imageBlobName">
                <Translate contentKey="kmsApp.paraProp.imageBlobName">Image Blob Name</Translate>
              </span>
              <UncontrolledTooltip target="imageBlobName">
                <Translate contentKey="kmsApp.paraProp.help.imageBlobName" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraPropEntity.imageBlobName}</dd>
            <dt>
              <span id="usingFlag">
                <Translate contentKey="kmsApp.paraProp.usingFlag">Using Flag</Translate>
              </span>
              <UncontrolledTooltip target="usingFlag">
                <Translate contentKey="kmsApp.paraProp.help.usingFlag" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraPropEntity.usingFlag ? 'true' : 'false'}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="kmsApp.paraProp.remarks">Remarks</Translate>
              </span>
              <UncontrolledTooltip target="remarks">
                <Translate contentKey="kmsApp.paraProp.help.remarks" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraPropEntity.remarks}</dd>
            <dt>
              <span id="validType">
                <Translate contentKey="kmsApp.paraProp.validType">Valid Type</Translate>
              </span>
              <UncontrolledTooltip target="validType">
                <Translate contentKey="kmsApp.paraProp.help.validType" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraPropEntity.validType}</dd>
            <dt>
              <span id="validBegin">
                <Translate contentKey="kmsApp.paraProp.validBegin">Valid Begin</Translate>
              </span>
              <UncontrolledTooltip target="validBegin">
                <Translate contentKey="kmsApp.paraProp.help.validBegin" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraPropEntity.validBegin} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="validEnd">
                <Translate contentKey="kmsApp.paraProp.validEnd">Valid End</Translate>
              </span>
              <UncontrolledTooltip target="validEnd">
                <Translate contentKey="kmsApp.paraProp.help.validEnd" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraPropEntity.validEnd} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createTime">
                <Translate contentKey="kmsApp.paraProp.createTime">Create Time</Translate>
              </span>
              <UncontrolledTooltip target="createTime">
                <Translate contentKey="kmsApp.paraProp.help.createTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraPropEntity.createTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="modifyTime">
                <Translate contentKey="kmsApp.paraProp.modifyTime">Modify Time</Translate>
              </span>
              <UncontrolledTooltip target="modifyTime">
                <Translate contentKey="kmsApp.paraProp.help.modifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraPropEntity.modifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="verifyTime">
                <Translate contentKey="kmsApp.paraProp.verifyTime">Verify Time</Translate>
              </span>
              <UncontrolledTooltip target="verifyTime">
                <Translate contentKey="kmsApp.paraProp.help.verifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraPropEntity.verifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="kmsApp.paraProp.createdUser">Created User</Translate>
            </dt>
            <dd>{paraPropEntity.createdUserName ? paraPropEntity.createdUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraProp.modifiedUser">Modified User</Translate>
            </dt>
            <dd>{paraPropEntity.modifiedUserName ? paraPropEntity.modifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraProp.verifiedUser">Verified User</Translate>
            </dt>
            <dd>{paraPropEntity.verifiedUserName ? paraPropEntity.verifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraProp.parent">Parent</Translate>
            </dt>
            <dd>{paraPropEntity.parentName ? paraPropEntity.parentName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/para-prop-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/para-prop-sdm-suffix/${paraPropEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ paraProp }: IRootState) => ({
  paraPropEntity: paraProp.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ParaPropSdmSuffixDetail);
