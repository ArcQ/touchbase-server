import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './chat.reducer';
import { IChat } from 'app/shared/model/chat.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IChatDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ChatDetail = (props: IChatDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { chatEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="touchbaseApp.chat.detail.title">Chat</Translate> [<b>{chatEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="createdDate">
              <Translate contentKey="touchbaseApp.chat.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{chatEntity.createdDate ? <TextFormat value={chatEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="lastModifiedDate">
              <Translate contentKey="touchbaseApp.chat.lastModifiedDate">Last Modified Date</Translate>
            </span>
          </dt>
          <dd>
            {chatEntity.lastModifiedDate ? <TextFormat value={chatEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="chatpiChatId">
              <Translate contentKey="touchbaseApp.chat.chatpiChatId">Chatpi Chat Id</Translate>
            </span>
          </dt>
          <dd>{chatEntity.chatpiChatId}</dd>
          <dt>
            <Translate contentKey="touchbaseApp.chat.base">Base</Translate>
          </dt>
          <dd>{chatEntity.baseId ? chatEntity.baseId : ''}</dd>
        </dl>
        <Button tag={Link} to="/chat" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/chat/${chatEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ chat }: IRootState) => ({
  chatEntity: chat.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ChatDetail);
