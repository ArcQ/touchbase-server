import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './base.reducer';
import { IBase } from 'app/shared/model/base.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBaseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BaseDetail = (props: IBaseDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { baseEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="touchbaseApp.base.detail.title">Base</Translate> [<b>{baseEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="createdDate">
              <Translate contentKey="touchbaseApp.base.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{baseEntity.createdDate ? <TextFormat value={baseEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="lastModifiedDate">
              <Translate contentKey="touchbaseApp.base.lastModifiedDate">Last Modified Date</Translate>
            </span>
          </dt>
          <dd>
            {baseEntity.lastModifiedDate ? <TextFormat value={baseEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="name">
              <Translate contentKey="touchbaseApp.base.name">Name</Translate>
            </span>
          </dt>
          <dd>{baseEntity.name}</dd>
          <dt>
            <span id="score">
              <Translate contentKey="touchbaseApp.base.score">Score</Translate>
            </span>
          </dt>
          <dd>{baseEntity.score}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="touchbaseApp.base.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{baseEntity.imageUrl}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="touchbaseApp.base.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{baseEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="touchbaseApp.base.creator">Creator</Translate>
          </dt>
          <dd>{baseEntity.creatorId ? baseEntity.creatorId : ''}</dd>
        </dl>
        <Button tag={Link} to="/base" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/base/${baseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ base }: IRootState) => ({
  baseEntity: base.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BaseDetail);
