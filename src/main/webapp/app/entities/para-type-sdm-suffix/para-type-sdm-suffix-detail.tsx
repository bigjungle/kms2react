import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './para-type-sdm-suffix.reducer';
import { IParaTypeSdmSuffix } from 'app/shared/model/para-type-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParaTypeSdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ParaTypeSdmSuffixDetail extends React.Component<IParaTypeSdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { paraTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.paraType.detail.title">ParaType</Translate> [<b>{paraTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="kmsApp.paraType.name">Name</Translate>
              </span>
              <UncontrolledTooltip target="name">
                <Translate contentKey="kmsApp.paraType.help.name" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraTypeEntity.name}</dd>
            <dt>
              <span id="serialNumber">
                <Translate contentKey="kmsApp.paraType.serialNumber">Serial Number</Translate>
              </span>
              <UncontrolledTooltip target="serialNumber">
                <Translate contentKey="kmsApp.paraType.help.serialNumber" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraTypeEntity.serialNumber}</dd>
            <dt>
              <span id="sortString">
                <Translate contentKey="kmsApp.paraType.sortString">Sort String</Translate>
              </span>
              <UncontrolledTooltip target="sortString">
                <Translate contentKey="kmsApp.paraType.help.sortString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraTypeEntity.sortString}</dd>
            <dt>
              <span id="descString">
                <Translate contentKey="kmsApp.paraType.descString">Desc String</Translate>
              </span>
              <UncontrolledTooltip target="descString">
                <Translate contentKey="kmsApp.paraType.help.descString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraTypeEntity.descString}</dd>
            <dt>
              <span id="imageBlob">
                <Translate contentKey="kmsApp.paraType.imageBlob">Image Blob</Translate>
              </span>
              <UncontrolledTooltip target="imageBlob">
                <Translate contentKey="kmsApp.paraType.help.imageBlob" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              {paraTypeEntity.imageBlob ? (
                <div>
                  <a onClick={openFile(paraTypeEntity.imageBlobContentType, paraTypeEntity.imageBlob)}>
                    <img
                      src={`data:${paraTypeEntity.imageBlobContentType};base64,${paraTypeEntity.imageBlob}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {paraTypeEntity.imageBlobContentType}, {byteSize(paraTypeEntity.imageBlob)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="imageBlobName">
                <Translate contentKey="kmsApp.paraType.imageBlobName">Image Blob Name</Translate>
              </span>
              <UncontrolledTooltip target="imageBlobName">
                <Translate contentKey="kmsApp.paraType.help.imageBlobName" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraTypeEntity.imageBlobName}</dd>
            <dt>
              <span id="usingFlag">
                <Translate contentKey="kmsApp.paraType.usingFlag">Using Flag</Translate>
              </span>
              <UncontrolledTooltip target="usingFlag">
                <Translate contentKey="kmsApp.paraType.help.usingFlag" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraTypeEntity.usingFlag ? 'true' : 'false'}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="kmsApp.paraType.remarks">Remarks</Translate>
              </span>
              <UncontrolledTooltip target="remarks">
                <Translate contentKey="kmsApp.paraType.help.remarks" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraTypeEntity.remarks}</dd>
            <dt>
              <span id="validType">
                <Translate contentKey="kmsApp.paraType.validType">Valid Type</Translate>
              </span>
              <UncontrolledTooltip target="validType">
                <Translate contentKey="kmsApp.paraType.help.validType" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraTypeEntity.validType}</dd>
            <dt>
              <span id="validBegin">
                <Translate contentKey="kmsApp.paraType.validBegin">Valid Begin</Translate>
              </span>
              <UncontrolledTooltip target="validBegin">
                <Translate contentKey="kmsApp.paraType.help.validBegin" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraTypeEntity.validBegin} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="validEnd">
                <Translate contentKey="kmsApp.paraType.validEnd">Valid End</Translate>
              </span>
              <UncontrolledTooltip target="validEnd">
                <Translate contentKey="kmsApp.paraType.help.validEnd" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraTypeEntity.validEnd} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createTime">
                <Translate contentKey="kmsApp.paraType.createTime">Create Time</Translate>
              </span>
              <UncontrolledTooltip target="createTime">
                <Translate contentKey="kmsApp.paraType.help.createTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraTypeEntity.createTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="modifyTime">
                <Translate contentKey="kmsApp.paraType.modifyTime">Modify Time</Translate>
              </span>
              <UncontrolledTooltip target="modifyTime">
                <Translate contentKey="kmsApp.paraType.help.modifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraTypeEntity.modifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="verifyTime">
                <Translate contentKey="kmsApp.paraType.verifyTime">Verify Time</Translate>
              </span>
              <UncontrolledTooltip target="verifyTime">
                <Translate contentKey="kmsApp.paraType.help.verifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraTypeEntity.verifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="kmsApp.paraType.createdUser">Created User</Translate>
            </dt>
            <dd>{paraTypeEntity.createdUserName ? paraTypeEntity.createdUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraType.modifiedUser">Modified User</Translate>
            </dt>
            <dd>{paraTypeEntity.modifiedUserName ? paraTypeEntity.modifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraType.verifiedUser">Verified User</Translate>
            </dt>
            <dd>{paraTypeEntity.verifiedUserName ? paraTypeEntity.verifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraType.parent">Parent</Translate>
            </dt>
            <dd>{paraTypeEntity.parentName ? paraTypeEntity.parentName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/para-type-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/para-type-sdm-suffix/${paraTypeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ paraType }: IRootState) => ({
  paraTypeEntity: paraType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ParaTypeSdmSuffixDetail);
