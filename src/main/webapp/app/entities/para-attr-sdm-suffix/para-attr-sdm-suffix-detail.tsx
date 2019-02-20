import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './para-attr-sdm-suffix.reducer';
import { IParaAttrSdmSuffix } from 'app/shared/model/para-attr-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParaAttrSdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ParaAttrSdmSuffixDetail extends React.Component<IParaAttrSdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { paraAttrEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.paraAttr.detail.title">ParaAttr</Translate> [<b>{paraAttrEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="kmsApp.paraAttr.name">Name</Translate>
              </span>
              <UncontrolledTooltip target="name">
                <Translate contentKey="kmsApp.paraAttr.help.name" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraAttrEntity.name}</dd>
            <dt>
              <span id="serialNumber">
                <Translate contentKey="kmsApp.paraAttr.serialNumber">Serial Number</Translate>
              </span>
              <UncontrolledTooltip target="serialNumber">
                <Translate contentKey="kmsApp.paraAttr.help.serialNumber" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraAttrEntity.serialNumber}</dd>
            <dt>
              <span id="sortString">
                <Translate contentKey="kmsApp.paraAttr.sortString">Sort String</Translate>
              </span>
              <UncontrolledTooltip target="sortString">
                <Translate contentKey="kmsApp.paraAttr.help.sortString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraAttrEntity.sortString}</dd>
            <dt>
              <span id="descString">
                <Translate contentKey="kmsApp.paraAttr.descString">Desc String</Translate>
              </span>
              <UncontrolledTooltip target="descString">
                <Translate contentKey="kmsApp.paraAttr.help.descString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraAttrEntity.descString}</dd>
            <dt>
              <span id="imageBlob">
                <Translate contentKey="kmsApp.paraAttr.imageBlob">Image Blob</Translate>
              </span>
              <UncontrolledTooltip target="imageBlob">
                <Translate contentKey="kmsApp.paraAttr.help.imageBlob" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              {paraAttrEntity.imageBlob ? (
                <div>
                  <a onClick={openFile(paraAttrEntity.imageBlobContentType, paraAttrEntity.imageBlob)}>
                    <img
                      src={`data:${paraAttrEntity.imageBlobContentType};base64,${paraAttrEntity.imageBlob}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {paraAttrEntity.imageBlobContentType}, {byteSize(paraAttrEntity.imageBlob)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="imageBlobName">
                <Translate contentKey="kmsApp.paraAttr.imageBlobName">Image Blob Name</Translate>
              </span>
              <UncontrolledTooltip target="imageBlobName">
                <Translate contentKey="kmsApp.paraAttr.help.imageBlobName" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraAttrEntity.imageBlobName}</dd>
            <dt>
              <span id="usingFlag">
                <Translate contentKey="kmsApp.paraAttr.usingFlag">Using Flag</Translate>
              </span>
              <UncontrolledTooltip target="usingFlag">
                <Translate contentKey="kmsApp.paraAttr.help.usingFlag" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraAttrEntity.usingFlag ? 'true' : 'false'}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="kmsApp.paraAttr.remarks">Remarks</Translate>
              </span>
              <UncontrolledTooltip target="remarks">
                <Translate contentKey="kmsApp.paraAttr.help.remarks" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraAttrEntity.remarks}</dd>
            <dt>
              <span id="validType">
                <Translate contentKey="kmsApp.paraAttr.validType">Valid Type</Translate>
              </span>
              <UncontrolledTooltip target="validType">
                <Translate contentKey="kmsApp.paraAttr.help.validType" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraAttrEntity.validType}</dd>
            <dt>
              <span id="validBegin">
                <Translate contentKey="kmsApp.paraAttr.validBegin">Valid Begin</Translate>
              </span>
              <UncontrolledTooltip target="validBegin">
                <Translate contentKey="kmsApp.paraAttr.help.validBegin" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraAttrEntity.validBegin} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="validEnd">
                <Translate contentKey="kmsApp.paraAttr.validEnd">Valid End</Translate>
              </span>
              <UncontrolledTooltip target="validEnd">
                <Translate contentKey="kmsApp.paraAttr.help.validEnd" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraAttrEntity.validEnd} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createTime">
                <Translate contentKey="kmsApp.paraAttr.createTime">Create Time</Translate>
              </span>
              <UncontrolledTooltip target="createTime">
                <Translate contentKey="kmsApp.paraAttr.help.createTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraAttrEntity.createTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="modifyTime">
                <Translate contentKey="kmsApp.paraAttr.modifyTime">Modify Time</Translate>
              </span>
              <UncontrolledTooltip target="modifyTime">
                <Translate contentKey="kmsApp.paraAttr.help.modifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraAttrEntity.modifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="verifyTime">
                <Translate contentKey="kmsApp.paraAttr.verifyTime">Verify Time</Translate>
              </span>
              <UncontrolledTooltip target="verifyTime">
                <Translate contentKey="kmsApp.paraAttr.help.verifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraAttrEntity.verifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="kmsApp.paraAttr.createdUser">Created User</Translate>
            </dt>
            <dd>{paraAttrEntity.createdUserName ? paraAttrEntity.createdUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraAttr.modifiedUser">Modified User</Translate>
            </dt>
            <dd>{paraAttrEntity.modifiedUserName ? paraAttrEntity.modifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraAttr.verifiedUser">Verified User</Translate>
            </dt>
            <dd>{paraAttrEntity.verifiedUserName ? paraAttrEntity.verifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraAttr.parent">Parent</Translate>
            </dt>
            <dd>{paraAttrEntity.parentName ? paraAttrEntity.parentName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/para-attr-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/para-attr-sdm-suffix/${paraAttrEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ paraAttr }: IRootState) => ({
  paraAttrEntity: paraAttr.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ParaAttrSdmSuffixDetail);
