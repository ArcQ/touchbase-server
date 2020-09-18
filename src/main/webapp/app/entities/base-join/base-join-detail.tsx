import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './base-join.reducer';
import { IBaseJoin } from 'app/shared/model/base-join.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBaseJoinDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BaseJoinDetail = (props: IBaseJoinDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { baseJoinEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="touchbaseApp.baseJoin.detail.title">BaseJoin</Translate> [<b>{baseJoinEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="baseJoin">
              <Translate contentKey="touchbaseApp.baseJoin.baseJoin">Base Join</Translate>
            </span>
          </dt>
          <dd>{baseJoinEntity.baseJoin}</dd>
          <dt>
            <Translate contentKey="touchbaseApp.baseJoin.base">Base</Translate>
          </dt>
          <dd>{baseJoinEntity.baseId ? baseJoinEntity.baseId : ''}</dd>
        </dl>
        <Button tag={Link} to="/base-join" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/base-join/${baseJoinEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ baseJoin }: IRootState) => ({
  baseJoinEntity: baseJoin.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BaseJoinDetail);
